package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

import java.util.*;

/**
 * IR representation of a grammar
 *
 * Author: Domi
 * Date: May 29, 2011
 * Time: 12:35:59 PM
 */
public class GrammarExpr extends GrammarClassDeclTemplate {
    public final JCTree.JCExpression TokenType;
    public final JCTree.JCExpression TokenNameType;

    /**
     * The set of all passes
     */
    public final List<PassDeclaration> PassDeclarations;
    public final GrammarBaseASTNodeDeclTemplate BaseNodeTempl;
    public final List<JCTree.JCExpression> Interfaces;
    public final List<JCTree> AdditionalMembers;

    public final List<NonTerminal> NonTerminals;
    public final Map<Name, NonTerminal> NonTerminalMap = new HashMap<Name, NonTerminal>();
    public final Map<Name, Terminal> Terminals = new HashMap<Name, Terminal>();
    public final ArrayList<Terminal> TerminalList = new ArrayList<Terminal>();


    public GrammarExpr(int pos, Name name, JCTree.JCExpression tokenType, JCTree.JCExpression tokenNameType,
                       List<PassDeclaration> passDeclarations, GrammarBaseASTNodeDeclTemplate baseNodeTempl,
                       List<JCTree.JCExpression> interfaces,
                       List<JCTree> additionalMembers, List<NonTerminal> nonTerminals) {
        super(pos, name, interfaces, additionalMembers);

        TokenType = tokenType;
        TokenNameType = tokenNameType;
        PassDeclarations = passDeclarations;
        BaseNodeTempl = baseNodeTempl;
        Interfaces = interfaces;
        AdditionalMembers = additionalMembers;

        NonTerminals = nonTerminals;

//        for (NonTerminal var : NonTerminals) {
//            var.Grammar = this;
//        }
    }

    public boolean init(Parser parser) {
        return
                fillNonTerminalMap(parser) &&
                        resolveSymbols() &&
                        verifyPasses(parser) &&
                        determinePassPropagation();
    }

    public PassDeclaration getPassDeclaration(Name name) {
        for (PassDeclaration passDecl : PassDeclarations) {
            if (name.equals(passDecl.Name)) {
                return passDecl;
            }
        }
        return null;
    }

    private boolean fillNonTerminalMap(Parser parser) {
        for (NonTerminal var : NonTerminals) {
            if (NonTerminalMap.containsKey(var.Name)) {
                // symbol defined twice
                parser.syntaxError(var.Pos, "compiler.err.grammar.nonterminal.definedtwice", var.Name);
                return false;
            }
            else {
                NonTerminalMap.put(var.Name, var);
            }
        }
        return true;
    }

    /**
     * Resolves all symbols and returns a list of all Terminals
     */
    private boolean resolveSymbols() {
        int terminalIndex = 0;
        for (NonTerminal var : NonTerminals) {
            for (Rule rule : var.Rules) {
                for (Symbol sym : rule.Symbols) {
                    NonTerminal varOccurrence = NonTerminalMap.get(sym.Name);
                    if (varOccurrence == null) {
                        // Terminal
                        Terminal terminal = Terminals.get(sym.Name);
                        if (terminal == null) {
                            // first occurrence of Terminal
                            Terminals.put(sym.Name, terminal = new Terminal(sym.Name, terminalIndex++));
                            TerminalList.add(terminal);
                        }
                        terminal.Symbols.add(sym);
                        sym.Terminal = terminal;
                    }
                    else {
                        // NonTerminal
                        sym.NonTerminal = varOccurrence;
                    }
                }
            }
        }
        return true;
    }

    /**
     * All rules of a NonTerminal must define the same parameters for each pass.
     */
    private boolean verifyPasses(Parser parser) {
        for (NonTerminal var : NonTerminals) {

            // first create a map of all passes that share the same name
            Map<Name, ArrayList<Rule>> passMap = new HashMap<Name, ArrayList<Rule>>();
            for (int i = 0, RulesSize = var.Rules.size(); i < RulesSize; i++) {
                Rule rule = var.Rules.get(i);

                for (int j = 0, PassesSize = rule.Passes.size(); j < PassesSize; j++) {
                    Pass pass = rule.Passes.get(j);
                    PassDeclaration passDecl = getPassDeclaration(pass.Name);
                    if (passDecl == null) {
                        // pass is not declared
                        parser.syntaxError(var.Pos, "compiler.err.grammar.pass.doesnotexist",
                                pass.Name, rule.Index, var.Name);
                        return false;
                    }

                    pass.Decl = passDecl;
                    ArrayList<Rule> list = passMap.get(pass.Name);
                    if (list == null) {
                        passMap.put(pass.Name, list = new ArrayList<Rule>());
                    }
                    list.add(rule);
                }
            }

            // make sure all passes match
            for (Map.Entry<Name, ArrayList<Rule>> entry : passMap.entrySet()) {             // one iteration per pass
                Rule firstRule = entry.getValue().get(0);
                Pass firstPass = firstRule.getPass(entry.getKey());
                
                ArrayList<Rule> rules = entry.getValue();
                for (int i = 0, valueSize = rules.size(); i < valueSize; i++) {             // check that pass against all rules
                    Rule rule = rules.get(i);
                    Pass pass = rule.getPass(entry.getKey());

                    // use lazy string comparison, since AST node comparison is way too complicated\
                    // and apparently not implemented by default:
                    if (!firstPass.Parameters.toString().equals(pass.Parameters.toString())) {
                        parser.syntaxError(pass.Pos, "compiler.err.grammar.pass.signature.inconsistent",
                                pass.Name, rule.Index, var.Name, pass.Name, firstRule.Index);
                        return false;
                    }
                }
            }

            // create per-NonTerminal pass list
            ListBuffer<Pass> passes = new ListBuffer<Pass>();
            for (Map.Entry<Name, ArrayList<Rule>> entry : passMap.entrySet()) {
                Rule rule = entry.getValue().get(0);
                passes.add(rule.getPass(entry.getKey()));
            }
            var.Passes = passes.toList();
        }
        return true;
    }
    
    private boolean determinePassPropagation() {
        for (NonTerminal var : NonTerminals) {
            for (Rule rule : var.Rules) {
                rule.PropagationStatus = new boolean[PassDeclarations.length()];

                for (GrammarExpr.PassDeclaration passDecl : PassDeclarations) {
                    rule.PropagationStatus[passDecl.Index] = true;
                    // can only propagate passes if no child's pass implementation has parameters
                    for (GrammarExpr.Symbol sym : rule.Symbols) {
                        if (sym.NonTerminal != null) {                // only propagate NonTerminal nodes
                            GrammarExpr.Pass pass = sym.NonTerminal.getPass(passDecl.Name);
                            if (pass != null && pass.hasParameters()) {
                                // cannot propagate because at least one of the symbol's passes has a different signature
                                rule.PropagationStatus[passDecl.Index] =  false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }



    // classes that together build a GrammarExpr


    /**
     * A pass consists of a Type and a Name.
     * Parameters of a pass depend on the context.
     */
    public static class PassDeclaration {
        public JCTree.JCExpression ReturnType;
        public com.sun.tools.javac.util.Name Name;
        public int Index;

        public PassDeclaration(JCTree.JCExpression type, Name name,int index) {
            ReturnType = type;
            Name = name;
            Index = index;
        }
    }

    /**
     * Every one of these becomes an abstract ASTNode class
     */
    public static class NonTerminal extends GrammarClassDeclTemplate {
        static int lastId;

//        public GrammarExpr Grammar;
        public final int Id;
        public List<Rule> Rules;
        public List<Pass> Passes;

        public NonTerminal(int pos, Name name, List<JCTree.JCExpression> interfaces, List<JCTree> additionalMembers,
                           List<Rule> rules) {
            this(pos, ++lastId, name, interfaces, additionalMembers, rules);
        }

        public NonTerminal(int pos, int id, Name name, List<JCTree.JCExpression> interfaces, List<JCTree> additionalMembers,
                           List<Rule> rules) {
            super(pos, name, interfaces, additionalMembers);

            Id = id;
            Rules = rules;

//            for (Rule rule : Rules) {
//                rule.DefniningNonTerminal = this;
//            }
        }

        public Pass getPass(Name name) {
            for (Pass pass : Passes) {
                if (name.equals(pass.Name)) {
                    return pass;
                }
            }
            return null;
        }
    }

    /**
     *
     */
    public static class Rule extends GrammarClassDeclTemplate {
//        public NonTerminal DefniningNonTerminal;

        public List<Symbol> Symbols;
        public final List<Pass> Passes;
        public final int Index;
        
        public boolean[] PropagationStatus;

        public Rule(int pos, int index, 
                    Name name, List<JCTree.JCExpression> interfaces, List<JCTree> additionalMembers,
                    List<Symbol> symbols, List<Pass> passes) {
            super(pos, name, interfaces, additionalMembers);

            Index = index;
            Symbols = symbols;
            Passes = passes;
        }

        public Pass getPass(Name name) {
            for (Pass p : Passes) {
                if (p.Name.equals(name)) {
                    return p;
                }
            }
            return null;
        }

        public boolean canPropagatePass(PassDeclaration pass) {
            return PropagationStatus[pass.Index];
        }
    }

    /**
     * Will be converted into a method
     */
    public static class Pass extends GrammarASTTemplate {
        public final List<JCTree.JCVariableDecl> Parameters;
        public final JCTree.JCBlock Code;
        public PassDeclaration Decl;

        public Pass(int pos, Name name, List<JCTree.JCVariableDecl> formalParameters, JCTree.JCBlock code) {
            super(pos, name);

            Parameters = formalParameters;
            Code = code;
        }

        public boolean hasParameters() {
            return Parameters.length() > 0;
        }
    }

    public static class Terminal {
        public Name Name;
        public final ArrayList<Symbol> Symbols = new ArrayList<Symbol>();
        public final int Index;

        public Terminal(Name name, int index) {
            Name = name;
            Index = index;
        }
    }

    public static class Symbol {
        public int Pos;
        public Name Name;
        public int Index;

        /**
         * The NonTerminal that represents this Symbol or null, if this is a Terminal symbol
         */
        public NonTerminal NonTerminal;
        /**
         * The Terminal that represents this Symbol or null, if this is a NonTerminal symbol
         */
        public Terminal Terminal;

        public Symbol(int pos, int index, Name name) {
            Pos = pos;
            Index = index;
            Name = name;
        }

        public String getNodeTypeName() {
            return NonTerminal != null ? GrammarNames.nonTerminalNodeClass(NonTerminal) : GrammarNames.TerminalNode;
        }
    }
}