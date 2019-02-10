package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTags;
import com.sun.tools.javac.parser.Lexer;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeCopier;
import com.sun.tools.javac.tree.TreeMaker;

import com.sun.tools.javac.util.*;

import javax.lang.model.type.TypeKind;

/**
 * Date: May 17, 2011
 *
 * Domi edit: Converts a parsed grammar declaration into a set of class declarations
 */
public class GrammarToClassConverter {
    static final long PublicStaticFinalFlags = Flags.PUBLIC | Flags.STATIC | Flags.FINAL;
    static final long PublicStaticAbstractFlags = Flags.PUBLIC | Flags.STATIC | Flags.ABSTRACT;
    static final long PublicFinalFlags = Flags.PUBLIC | Flags.FINAL;
    static final long PublicAbstractFlags = Flags.PUBLIC | Flags.ABSTRACT;
    static final long PublicFlags = Flags.PUBLIC;
    static final long ProtectedFlags = Flags.PROTECTED;
    static final long PrivateFlags = Flags.PRIVATE;
    static final long PrivateFinalFlags = PrivateFlags | Flags.FINAL;
    static final long AbstractFlags = Flags.ABSTRACT;

    final Parser Parser;
    final Lexer S;
    final TreeMaker F;
    final Log Log;
    final Name.Table Names;
    final TreeCopier<JCTree> Copier;

    /**
     * Currently processed grammar expression
     */
    GrammarExpr grammar;

    public GrammarToClassConverter(Parser parser, Lexer s, TreeMaker f, Log log, Name.Table names) {
        Parser = parser;
        S = s;
        F = f;
        Log = log;
        Names = names;
        Copier = new TreeCopier<JCTree>(F);
    }

    // TODO: 1. Make sure passes of all rules within one NonTerminal all have the same parameter declarations if they have the same name  
    public JCTree.JCClassDecl convert(GrammarExpr grammar, JCTree.JCModifiers mods) {
        mods.flags |= Flags.FINAL &~ Flags.ABSTRACT;          // make class final (cannot be abstract)

        JCTree.JCClassDecl grammarClss = F.at(grammar.Pos).ClassDef(mods,
                grammar.Name,                           // name
                List.<JCTree.JCTypeParameter>nil(),     // type parameters
                null,                                   // super class
                grammar.Interfaces,                     // implementing interfaces
                genBody(grammar));                      // member declarations
        return grammarClss;
    }

    public Name makeName(String name) {
        return Names.fromString(name);
    }

    /**
     * Convenient method to create a variable declaration
     */

    // ###############################################################################################
    // grammar body

    /**
     * Generates the body of the class that represents the given grammar, including the set of all
     * classes that represent the non-terminals and rules
     */
    public List<JCTree> genBody(GrammarExpr grammar) {
        this.grammar = grammar;

        ListBuffer<JCTree> members = new ListBuffer<JCTree>();

        // name attribute
        members.append(varDefString(PublicStaticFinalFlags, "GrammarName", "CMMGrammar"));

        // array of all Terminals
        members.append(varDef(PublicFinalFlags, GrammarNames.Terminals, arrayType(GrammarNames.Terminal),
                terminalArray()));

        // singleton instances of all NonTerminals
        for (GrammarExpr.NonTerminal var : grammar.NonTerminals) {
            members.append(varDef(PublicFinalFlags,
                    GrammarNames.nonTerminalInstance(var),
                    GrammarNames.nonTerminalClass(var),
                    newClassInstance(GrammarNames.nonTerminalClass(var))
            ));
        }

        // array of all NonTerminals
        // TODO: Should not be public since it must not be tempered with
        members.append(varDef(PublicFinalFlags, GrammarNames.NonTerminals, arrayType(GrammarNames.NonTerminal),
                nonTerminalArray()));

        // grammar default ctor to initialize all Rules
        if (!checkCtors()) {
            // does not have any ctor
            members.append(ctor(PublicFlags, block(callToInit())));
        }

        // method to initialize all Rules
        members.append(method(PrivateFinalFlags, GrammarNames._initSymbols,
                makeParams(),
                block(initializeSymbolsMethod())));

        // createAST method: "return new AST();"
        members.append(method(PublicFlags, "createAST", GrammarNames.ASTClass,
                block((F.Return(newClassInstance(GrammarNames.ASTClass))))));


        // Symbol class
        members.append(classDecl(PublicStaticAbstractFlags,
                GrammarNames.Symbol,
                List.<JCTree>nil()));       // currently, has no members

        // Terminal class
        members.append(classDecl(PublicStaticFinalFlags,
                GrammarNames.Terminal,
                GrammarNames.Symbol,
                terminalClassMembers()));

        // NonTerminal class
        members.append(classDecl(PublicStaticAbstractFlags,
                GrammarNames.NonTerminal,
                GrammarNames.Symbol,
                nonTerminalClassMembers()));

        // Rule class
        members.append(classDecl(PublicStaticAbstractFlags,
                GrammarNames.Rule,
                ruleClassMembers()));

        // AST class
        members.append(classDecl(PublicFinalFlags,
                GrammarNames.ASTClass,
                astMembers()));

        // ASTNodeList class
//        members.append(classDecl(PublicFinalFlags,
//                GrammarNames.ASTNodeList,
//                parametrizedType(GrammarNames.ListType, GrammarNames.Node),
//                List.<JCTree>nil()));

        // Node class
        members.append(classDecl(PublicAbstractFlags,
                GrammarNames.Node,
                (String)null,
                grammar.BaseNodeTempl != null ? grammar.BaseNodeTempl.Interfaces : List.<JCTree.JCExpression>nil(),
                nodeMembers()));

        // TerminalNode class
        members.append(classDecl(PublicFinalFlags,
                GrammarNames.TerminalNode,
                GrammarNames.Node,
                terminalNodeMembers()));

        // all NonTerminal singleton classes
        for (GrammarExpr.NonTerminal var : grammar.NonTerminals) {
            genNonTerminalSingletonClass(members, var);
        }

        // all NonTerminal & Rule AST nodes
        for (GrammarExpr.NonTerminal var : grammar.NonTerminals) {
            // One AST node class per NonTerminal
            members.append(classDecl(PublicAbstractFlags,
                    GrammarNames.nonTerminalNodeClass(var),
                    GrammarNames.Node,
                    var.Interfaces,
                    nonTerminalASTNodeMembers(var)));

            // One AST node class per Rule
            for (GrammarExpr.Rule rule : var.Rules) {
                members.append(
                        classDecl(PublicFinalFlags,
                                GrammarNames.ruleNodeClass(var, rule),
                                GrammarNames.nonTerminalNodeClass(var),
                                rule.Interfaces,
                                ruleASTNodeMembers(var, rule)));
            }
        }

        // custom members
        members.appendList(grammar.AdditionalMembers);

        return members.toList();
    }

    // ###############################################################################################
    // parts of the grammar body

    /**
     * Default ctor to initialize all rules
     */
    private List<JCTree.JCStatement> initializeSymbolsMethod() {
        ListBuffer<JCTree.JCStatement> stats = new ListBuffer<JCTree.JCStatement>();
        for (GrammarExpr.NonTerminal var : grammar.NonTerminals) {
            for (GrammarExpr.Rule rule : var.Rules) {
                for (GrammarExpr.Symbol sym : rule.Symbols) {
                    JCTree.JCExpression symbolInstance;
                    F.at(sym.Pos);
                    if (sym.NonTerminal != null) {          // represents NonTerminal
                        assert(sym.Terminal == null);

                        // get NonTerminal singleton instance by name
                        symbolInstance = qualident(GrammarNames.nonTerminalInstance(sym.NonTerminal));
                    }
                    else {                                  // represents Terminal
                        assert(sym.Terminal != null);

                        // get Terminal from Terminals array
                        symbolInstance = F.Indexed(
                                qualident(GrammarNames.Terminals),
                                F.Literal(sym.Terminal.Index));
                    }

                    // create statements all initialize statements:
                    // "StartSymbol.grammarRules[0].symbols[0] = ProgramSymbol;"
                    stats.append(F.Exec(F.Assign(
                            F.Indexed(
                                    F.Select(
                                            F.Indexed(
                                                    qualident(GrammarNames.nonTerminalInstance(var), GrammarNames.grammarRules),
                                                    F.Literal(rule.Index)),
                                            makeName(GrammarNames.symbols)),
                                    F.Literal(sym.Index)),
                            symbolInstance)));
                }
            }
        }

        F.at(grammar.Pos);
        return stats.toList();
    }


    /**
     * Creates an array of all Non-Terminal singleton instances
     */
    private JCTree.JCExpression nonTerminalArray() {
        JCTree.JCExpression type = qualident(GrammarNames.NonTerminal);
        ListBuffer<JCTree.JCExpression> elems = new ListBuffer<JCTree.JCExpression>();

        for (GrammarExpr.NonTerminal var : grammar.NonTerminals) {
            elems.append(qualident(GrammarNames.nonTerminalInstance(var)));
        }

        return F.NewArray(type,
                List.<JCTree.JCExpression>nil(),                                // dimensions
                elems.toList()                                                  // elements
        );
    }

    /**
     * Creates an array of all Terminals that occur as symbol of any NonTerminal
     */
    private JCTree.JCExpression terminalArray() {
        JCTree.JCExpression terminalType = qualident(GrammarNames.Terminal);
        ListBuffer<JCTree.JCExpression> elems = new ListBuffer<JCTree.JCExpression>();

        int terminalIndex = 0;
        for (GrammarExpr.Terminal terminal : grammar.TerminalList) {
            F.at(terminal.Symbols.get(0).Pos);                      // blame first symbol occurrence
            assert(terminalIndex == terminal.Index);
            terminalIndex++;

            elems.append(newClassInstance(terminalType,
                    F.Literal(terminal.Index), qualident(grammar.TokenNameType, terminal.Name.toString())));
        }

        F.at(grammar.Pos);      // errors from here on, are again the grammar's fault 

        return F.NewArray(terminalType,
                List.<JCTree.JCExpression>nil(),                        // dimensions
                elems.toList()                                          // elements
        );
    }

    private List<JCTree> terminalClassMembers() {
        ListBuffer<JCTree> members = treeList();

        // class variables
        members.append(varDefInt(PublicFinalFlags, GrammarNames.Id, null));
        members.append(varDef(PublicFinalFlags, GrammarNames.TokenName, grammar.TokenNameType, null));

        // ctor - assign variables
        members.append(ctor(PublicFlags,
                makeParams(
                        varDefInt(0, "id", null),
                        varDef(0, "tokenName", grammar.TokenNameType, null)),
                block(
                        assignStmt(GrammarNames.Id, "id"),
                        assignStmt(GrammarNames.TokenName, "tokenName"))));

        return members.toList();
    }

    private List<JCTree> nonTerminalClassMembers() {
        ListBuffer<JCTree> members = treeList();

        // class variables
        members.append(varDefInt(PublicFinalFlags, GrammarNames.Id, null));
        members.append(varDefString(PublicFinalFlags, GrammarNames.Name, null));
        members.append(varDef(ProtectedFlags, GrammarNames.grammarRules, arrayType(GrammarNames.Rule), null));

        // default ctor - assign variables
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDefInt(0, "id", null),
                        varDefString(0, "name", null)),
                block(
                        assignStmt(GrammarNames.Id, "id"),
                        assignStmt(GrammarNames.Name, "name"))));

        // Rule getRule(int i) method
        members.append(method(
                PublicFlags, GrammarNames.getRule, GrammarNames.Rule,
                makeParams(
                        varDefInt(0, "i", null))
                ,
                block(
                        F.Return(arrayAccess(GrammarNames.grammarRules, qualident("i")))
                )));

        // List<Rule> getRules() method
        members.append(method(
                PublicFlags,
                GrammarNames.getRules,
                F.TypeApply(qualident("java.util.List"), List.of(qualident(GrammarNames.Rule))),
                block(
                        F.Return(methodCall("java.util.Arrays.asList", qualident(GrammarNames.grammarRules)))
                )));

        return members.toList();
    }

    private List<JCTree> ruleClassMembers() {
        ListBuffer<JCTree> members = treeList();

        // class variables
        members.append(varDef(PublicFinalFlags, GrammarNames.NonTerminal,
                qualident(GrammarNames.NonTerminal), null));
        members.append(varDef(PrivateFlags, GrammarNames.symbols, arrayType(GrammarNames.Symbol), null));
        members.append(varDefInt(PublicFinalFlags, GrammarNames.Index, null));

        // default ctor - assign variables
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDef(0, "nonTerminal", GrammarNames.NonTerminal, null),
                        varDef(0, GrammarNames.symbols, arrayType(GrammarNames.Symbol), null),
                        varDefInt(0, "index", null)),
                block(
                        assignStmt(GrammarNames.NonTerminal, "nonTerminal"),
                        assignStmt(thisQualIdent(GrammarNames.symbols), "symbols"),
                        assignStmt(GrammarNames.Index, "index"))));

        // getSymbolCount() method
        members.append(method(
                PublicFlags, GrammarNames.getSymbolCount, intType(),
                block(
                        F.Return(qualident(GrammarNames.symbols + ".length"))
                )));

        // getSymbol(int) method
        members.append(method(
                PublicFlags, GrammarNames.getSymbol, GrammarNames.Symbol,
                makeParams(
                        varDefInt(0, "i", null))
                ,
                block(
                        F.Return(arrayAccess(GrammarNames.symbols, qualident("i")))
                )));

        // getSymbols() method
        members.append(method(
                PublicFlags,
                GrammarNames.getSymbols,
                F.TypeApply(qualident("java.util.List"), List.of(qualident(GrammarNames.Symbol))),
                block(
                        F.Return(methodCall("java.util.Arrays.asList", qualident(GrammarNames.symbols)))
                )));

        // newChild(int, Node) method
        members.append(method(
                AbstractFlags, GrammarNames.newChild, GrammarNames.Node,
                makeParams(
                        varDefInt(0, GrammarNames.index, null),
                        varDef(0, GrammarNames.node, GrammarNames.Node, null)
                ),
                null));


        return members.toList();
    }

    private List<JCTree> astMembers() {
        ListBuffer<JCTree> members = treeList();

        // Root member
        // The first Non-Terminal is the synthesized Start symbol
        GrammarExpr.NonTerminal var = grammar.NonTerminals.get(0);
        String rootType = GrammarNames.ruleNodeClass(var, var.Rules.get(0));
        members.append(varDef(PublicFinalFlags, GrammarNames.Root,
                rootType,
                newClassInstance(rootType, F.Literal(0), nullLiteral())));

        // protected, empty default ctor
        members.append(ctor(ProtectedFlags, block()));


        return members.toList();
    }

    private List<JCTree> terminalNodeMembers() {
        ListBuffer<JCTree> members = treeList();

        // class variables
        members.append(varDef(PublicFinalFlags, GrammarNames.Terminal, GrammarNames.Terminal, null));

        // protected default ctor
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDef(0, "parent", GrammarNames.Node, null),
                        varDef(0, "terminal", GrammarNames.Terminal, null),
                        varDef(0, "token", grammar.TokenType, null),
                        varDefInt(0, "index", null)
                ),
                block(
                        F.Exec(methodCall("super", "index", "parent", "token")),
                        assignStmt(GrammarNames.Terminal, "terminal")
                )));

        return members.toList();
    }

    private List<JCTree> nodeMembers() {
        ListBuffer<JCTree> members = treeList();

        // class variables
        members.append(varDef(PublicFinalFlags, GrammarNames.Parent, GrammarNames.Node, null));
        members.append(varDef(PublicFinalFlags, GrammarNames.Token, grammar.TokenType, null));
        members.append(varDef(PublicFinalFlags, GrammarNames.Rule, GrammarNames.Rule, null));
        members.append(varDefInt(PublicFinalFlags, GrammarNames.Index, null));

        // ctor for TerminalNodes
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDefInt(0, "index", null),
                        varDef(0, "parent", GrammarNames.Node, null),
                        varDef(0, "token", GrammarNames.Token, null)
                ),
                block(
                        assignStmt(GrammarNames.Index, "index"),
                        assignStmt(GrammarNames.Parent, "parent"),
                        assignStmt(GrammarNames.Token, "token"),
                        assignStmt(GrammarNames.Rule, nullLiteral())
                )));

        // ctor for Rule nodes
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDefInt(0, "index", null),
                        varDef(0, "parent", GrammarNames.Node, null),
                        varDef(0, "rule", GrammarNames.Rule, null)
                ),
                block(
                        assignStmt(GrammarNames.Index, "index"),
                        assignStmt(GrammarNames.Parent, "parent"),
                        assignStmt(GrammarNames.Token, nullLiteral()),
                        assignStmt(GrammarNames.Rule, "rule")
                )));

        // boolean isLambda() method
        members.append(method(PublicFlags, GrammarNames.isLambda, primitiveType(TypeTags.BOOLEAN),
                block(
                        // return Rule != null && Rule.getSymbolCount() == 0
                        F.Return(
                                F.Binary(JCTree.AND,
                                        F.Binary(JCTree.NE,
                                                qualident(GrammarNames.Rule), nullLiteral()),
                                        F.Binary(JCTree.EQ,
                                                methodCall(qualident(GrammarNames.Rule, GrammarNames.getSymbolCount)),
                                                F.Literal(0))))
                )));

        // Node newChild(int, Rule) method
        members.append(method(PublicFlags, GrammarNames.newChild,
                GrammarNames.Node,
                makeParams(
                        varDefInt(0, GrammarNames.index, null),
                        varDef(0, "childRule", GrammarNames.Rule, null)
                ),
                block(
                        varDef(0, GrammarNames.node, GrammarNames.Node,
                                methodCall(qualident("childRule", GrammarNames.newChild),
                                        GrammarNames.index, GrammarNames.This)),
                        F.Exec(methodCall(GrammarNames.setChild, GrammarNames.node)),
                        F.Return(qualident(GrammarNames.node))
                )));

        // TerminalNode newChild(...) method                                                
        members.append(method(PublicFlags, GrammarNames.newChild, GrammarNames.TerminalNode,
                List.of(                                                                          // parameters
                        varDefInt(0, "index", null),
                        varDef(0, "terminal", GrammarNames.Terminal, null),
                        varDef(0, "token", GrammarNames.Token, null)),
                block(
                        varDef(0, GrammarNames.node, GrammarNames.TerminalNode,
                                newClassInstance(GrammarNames.TerminalNode,
                                        qualident(GrammarNames.This),
                                        qualident("terminal"),
                                        qualident("token"),
                                        qualident(GrammarNames.index))
                        ),
                        F.Exec(methodCall(GrammarNames.setChild, GrammarNames.node)),
                        F.Return(qualident(GrammarNames.node))
                )
        ));

        // getChild(int) method
        // overridden in every NonTerminal node class
        members.append(method(PublicFlags, GrammarNames.getChild, GrammarNames.Node,
                makeParams(
                        varDefInt(0, GrammarNames.index, null)
                ),
                block(
                        F.Return(nullLiteral())
                )
        ));

        // empty setChild(Node) method
        // overridden in every NonTerminal node class
        members.append(method(PublicFlags, GrammarNames.setChild, voidType(),
                makeParams(varDef(0, GrammarNames.node, GrammarNames.Node, null)),
                block()));

        // index() method
        members.append(method(PublicFlags, GrammarNames.index, intType(),
                block(
                        F.Return(qualident(GrammarNames.Index))
                )
        ));

        // empty pass propagation; to be overridden by every inheriting class
        for (GrammarExpr.PassDeclaration pass : grammar.PassDeclarations) {
            members.append(method(PublicFlags, GrammarNames.passPropagationMethod(pass), Copier.copy(pass.ReturnType),
                    block()
            ));
        }

        // custom members of Node class
        if (grammar.BaseNodeTempl != null) {
            members.appendList(grammar.BaseNodeTempl.AdditionalMembers);
        }

        // F.Return(defaultValue(pass.ReturnType))
        return members.toList();
    }

    private void genNonTerminalSingletonClass(ListBuffer<JCTree> body, GrammarExpr.NonTerminal var) {
        F.at(var.Pos);
        body.append(classDecl(PublicFinalFlags, GrammarNames.nonTerminalClass(var),
                GrammarNames.NonTerminal,
                List.<JCTree>of(
                        // ctor
                        ctor(PrivateFlags, block(F.Exec(
                                methodCall(GrammarNames.Super,
                                        F.Literal(var.Id),
                                        F.Literal(var.Name.toString())
                                )),
                                assignStmt(GrammarNames.grammarRules, rulesArrayFor(var))
                        ))
                ).appendList(
                        // one class for each rule
                        ruleClasses(var))
        ));
        F.at(grammar.Pos);
    }

    private JCTree.JCNewArray rulesArrayFor(GrammarExpr.NonTerminal var) {
        ListBuffer<JCTree.JCExpression> elems = new ListBuffer<JCTree.JCExpression>();

        for (GrammarExpr.Rule rule : var.Rules) {
            F.at(rule.Pos);
            elems.append(newClassInstance(GrammarNames.ruleClass(rule),
                    F.NewArray(
                            qualident(GrammarNames.Symbol),
                            List.<JCTree.JCExpression>of(F.Literal(rule.Symbols.length())),
                            null)));
        }

        F.at(grammar.Pos);
        return F.NewArray(
                qualident(GrammarNames.Rule),
                List.<JCTree.JCExpression>nil(),
                elems.toList());
    }

    private List<JCTree> ruleClasses(GrammarExpr.NonTerminal var) {
        ListBuffer<JCTree> classes = treeList();

        for (GrammarExpr.Rule rule : var.Rules) {
            F.at(rule.Pos);

            // prepare Rule class members
            ListBuffer<JCTree> ruleMembers = treeList();

            // ctor
            ruleMembers.append(ctor(ProtectedFlags,
                    List.of(varDef(0, GrammarNames.symbols, arrayType(GrammarNames.Symbol), null)),
                    block(
                            // call super ctor
                            F.Exec(methodCall(GrammarNames.Super,
                                    qualident(GrammarNames.nonTerminalClass(var), GrammarNames.This),
                                    qualident(GrammarNames.symbols),
                                    F.Literal(rule.Index))))
            ));

            // override of newChild method
            ruleMembers.append(method(PublicFlags, GrammarNames.newChild, GrammarNames.Node,
                    makeParams(
                            varDefInt(0, GrammarNames.index, null),
                            varDef(0, GrammarNames.Parent, GrammarNames.Node, null)
                    ),
                    block(
                            F.Return(newClassInstance(GrammarNames.ruleNodeClass(var, rule),
                                    qualident(GrammarNames.index),
                                    qualident(GrammarNames.Parent)))
                    )
            ));

            // append class
            classes.append(classDecl(PublicFinalFlags, GrammarNames.ruleClass(rule),
                    GrammarNames.Rule,
                    ruleMembers.toList()));
        }

        return classes.toList();
    }

    private List<JCTree> nonTerminalASTNodeMembers(GrammarExpr.NonTerminal var) {
        F.at(var.Pos);
        ListBuffer<JCTree> members = treeList();

        // ctor
        members.append(ctor(ProtectedFlags,
                makeParams(
                        varDefInt(0, "index", null),
                        varDef(0, "parent", GrammarNames.Node, null),
                        varDef(0, "rule", GrammarNames.Rule, null)),
                block(
                        // call super ctor
                        F.Exec(methodCall(GrammarNames.Super,
                                qualident("index"),
                                qualident("parent"),
                                qualident("rule"))))
        ));

        // propagating passes
        for (GrammarExpr.PassDeclaration passDeclaration : grammar.PassDeclarations) {
            GrammarExpr.Pass pass = var.getPass(passDeclaration.Name);
            members.append(method(PublicAbstractFlags, GrammarNames.passMethod(passDeclaration),
                    Copier.copy(passDeclaration.ReturnType),
                    pass != null ?
                            Copier.copy(pass.Parameters) :
                            List.<JCTree.JCVariableDecl>nil(),
                    null));
        }

        // custom members
        members.appendList(var.AdditionalMembers);

        F.at(grammar.Pos);
        return members.toList();
    }

    private List<JCTree> ruleASTNodeMembers(GrammarExpr.NonTerminal var, GrammarExpr.Rule rule) {
        F.at(rule.Pos);
        ListBuffer<JCTree> members = treeList();

        // children of node: $1, $2, $3, ...
        for (GrammarExpr.Symbol sym : rule.Symbols) {
            members.append(varDef(PublicFlags, GrammarNames.astMemberName(sym), sym.getNodeTypeName(), null));
        }

        // ctor: Call super ctor with parent and rule arguments
        members.append(
                ctor(ProtectedFlags,
                        makeParams(
                                varDefInt(0, "index", null),
                                varDef(0, "parent", GrammarNames.Node, null)
                        ),
                        block(F.Exec(
                                methodCall(GrammarNames.Super,
                                        qualident("index"),
                                        qualident("parent"),
                                        methodCall(qualident(GrammarNames.nonTerminalInstance(var), GrammarNames.getRule),
                                                F.Literal(rule.Index)))
                        ))));

        // Node getChild(int index) method
        members.append(method(PublicFlags, GrammarNames.getChild, GrammarNames.Node,
                makeParams(
                        varDefInt(0, GrammarNames.index, null)
                ),
                block(
                        F.Switch(qualident(GrammarNames.index),
                                getChildCases(rule)),
                        F.Return(nullLiteral())
                )
        ));

        // void setChild(Node) method
        members.append(method(PublicFlags, GrammarNames.setChild, voidType(),
                makeParams(
                        varDef(0, GrammarNames.node, GrammarNames.Node, null)
                ),
                block(
                        F.Switch(methodCall(qualident(GrammarNames.node, GrammarNames.index)),
                                setChildCases(rule))
                )));

        // passes
        for (GrammarExpr.PassDeclaration passDecl : grammar.PassDeclarations) {
            // add default propagation method, if the pass can be propagated to the node's children 
            if (rule.canPropagatePass(passDecl)) {
                // simply call the pass to be propagated, on all children
                ListBuffer<JCTree.JCStatement> propagations = new ListBuffer<JCTree.JCStatement>();
                for (GrammarExpr.Symbol sym : rule.Symbols) {
                    if (sym.NonTerminal != null) {                // only propagate NonTerminal nodes
                        propagations.append(F.Exec(methodCall(
                                qualident(GrammarNames.astMemberName(sym), passDecl.Name.toString()))));
                    }
                }

                // add the propagation method
                members.append(method(PublicFlags, GrammarNames.passPropagationMethod(passDecl), voidType(),
                        F.Block(0, propagations.toList())
                ));
            }

            GrammarExpr.Pass pass = rule.getPass(passDecl.Name);

            // add pass method
            if (pass != null) {
                // developer defined pass explicitely - add it as-is
                members.append(method(PublicFlags, GrammarNames.passMethod(passDecl), Copier.copy(pass.Decl.ReturnType),
                        pass.Parameters, pass.Code));
            }
            else if (rule.canPropagatePass(passDecl)) {
                // default pass implementation:
                GrammarExpr.Pass nonTerminalPass = var.getPass(passDecl.Name);
                members.append(method(PublicFlags,
                        GrammarNames.passMethod(passDecl),
                        Copier.copy(passDecl.ReturnType),
                        nonTerminalPass != null ?
                                Copier.copy(nonTerminalPass.Parameters) : List.<JCTree.JCVariableDecl>nil(),
                        block(
                                F.Exec(methodCall(GrammarNames.passPropagationMethod(passDecl)))
                        )));
            }
            else {
                // cannot propagate pass due to parameter constraints
                // TODO: Warn or error
                System.err.format("ERROR in grammar %s - " +
                        "Pass %s cannot be propagated automatically and no custom implementation found in NonTerminal %s, Rule %d",
                        grammar.Name, passDecl.Name, var.Name, rule.Index);
            }
        }

        // custom members
        members.appendList(rule.AdditionalMembers);

        F.at(grammar.Pos);
        return members.toList();
    }

    private List<JCTree.JCCase> getChildCases(GrammarExpr.Rule rule) {
        ListBuffer<JCTree.JCCase> cases = new ListBuffer<JCTree.JCCase>();

        for (GrammarExpr.Symbol sym : rule.Symbols) {
            cases.append(F.Case(F.Literal(sym.Index),
                    List.<JCTree.JCStatement>of(
                            F.Return(
                                    //F.TypeCast(qualident(sym.getNodeTypeName()),
                                    qualident(GrammarNames.astMemberName(sym)
                                            //)
                                    ))
                    )));
        }

        return cases.toList();
    }

    private List<JCTree.JCCase> setChildCases(GrammarExpr.Rule rule) {
        ListBuffer<JCTree.JCCase> cases = new ListBuffer<JCTree.JCCase>();

        for (GrammarExpr.Symbol sym : rule.Symbols) {
            cases.append(F.Case(F.Literal(sym.Index),
                    List.<JCTree.JCStatement>of(
                            assignStmt(GrammarNames.astMemberName(sym),
//                                    sym.Terminal != null ?
//                                            qualident(GrammarNames.node) :
                                    F.TypeCast(qualident(sym.getNodeTypeName()), qualident(GrammarNames.node))),
                            F.Break(null))));
        }

        return cases.toList();
    }

    // ###############################################################################################
    // utility methods to create AST nodes

    /**
     * Convert a qualified identifier string into an AST node
     * TODO: Use Name instead of String to reduce GC overhead 
     */
    public JCTree.JCExpression qualident(String typeName) {
        if (typeName == null) {
            return null;
        }
        int start = typeName.indexOf('.');
        JCTree.JCExpression type;
        if (start == -1) {
            return F.Ident(makeName(typeName));
        }
        else {
            type = F.Ident(makeName(typeName.substring(0, start)));
            return qualident(type, typeName, start+1);
        }
    }

    public JCTree.JCExpression thisQualIdent(String selector) {
        return qualident(GrammarNames.This, selector);
    }

    public JCTree.JCExpression qualident(String typeName, String selector) {
        return qualident(F.Ident(makeName(typeName)), selector, 0);
    }

    public JCTree.JCExpression qualident(JCTree.JCExpression type, String selector) {
        return qualident(type, selector, 0);
    }

    public JCTree.JCExpression qualident(JCTree.JCExpression type, String selector, int start) {
        int end;
        do {
            end = selector.indexOf('.', start);
            if (end == -1) {
                type = F.Select(type, makeName(selector.substring(start)));
            }
            else {
                type = F.Select(type, makeName(selector.substring(start, end)));
            }
            start = end+1;
        }
        while (end != -1);
        return type;
    }

    public JCTree.JCVariableDecl varDefString(long flags, String name, String initValue) {
        return F.VarDef(F.Modifiers(flags), makeName(name), qualident("String"),
                initValue != null ? F.Literal(TypeTags.CLASS, initValue) : null);
    }
    public JCTree.JCVariableDecl varDefInt(long flags, String name, Object initValue) {
        return F.VarDef(F.Modifiers(flags), makeName(name), intType(), initValue != null ? F.Literal(TypeTags.INT, initValue) : null);
    }
    public JCTree.JCVariableDecl varDef(long flags, String name, String typeName, JCTree.JCExpression initValue) {
        return F.VarDef(F.Modifiers(flags), makeName(name), qualident(typeName), initValue);
    }
    public JCTree.JCVariableDecl varDef(long flags, String name, JCTree.JCExpression typeExpr, JCTree.JCExpression initValue) {
        return F.VarDef(F.Modifiers(flags), makeName(name), typeExpr, initValue);
    }

    public JCTree.JCNewClass newClassInstance(String type) {
        return F.NewClass(null, List.<JCTree.JCExpression>nil(), qualident(type), List.<JCTree.JCExpression>nil(), null);
    }
    public JCTree.JCNewClass newClassInstance(String type, JCTree.JCExpression... args) {
        return F.NewClass(null, List.<JCTree.JCExpression>nil(), qualident(type), List.from(args), null);
    }
    public JCTree.JCNewClass newClassInstance(JCTree.JCExpression type, JCTree.JCExpression... args) {
        return F.NewClass(null, List.<JCTree.JCExpression>nil(), type, List.from(args), null);
    }
    public JCTree.JCNewClass newClassInstance(String type, List<JCTree.JCExpression> typeArgs, JCTree.JCExpression... args) {
        return F.NewClass(null, typeArgs, qualident(type), List.from(args), null);
    }

    public JCTree.JCMethodDecl ctor(long flags, JCTree.JCBlock code) {
        return method(flags, Names.init, List.< JCTree.JCVariableDecl >nil(), code);
    }
    public JCTree.JCMethodDecl ctor(long flags, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, Names.init, params, code);
    }
    public JCTree.JCMethodDecl method(long flags, String name, String retType, JCTree.JCBlock code) {
        return method(flags, makeName(name), retType, List.< JCTree.JCVariableDecl >nil(), code);
    }
    public JCTree.JCMethodDecl method(long flags, String name, JCTree.JCExpression retType, JCTree.JCBlock code) {
        return method(flags, makeName(name), retType, List.< JCTree.JCVariableDecl >nil(), code);
    }
    public JCTree.JCMethodDecl method(long flags, String name, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, makeName(name), voidType(), params, code);
    }
    public JCTree.JCMethodDecl method(long flags, Name name, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, name, voidType(), params, code);
    }
    public JCTree.JCMethodDecl method(long flags, String name, String retType, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, makeName(name), retType, params, code);
    }
    public JCTree.JCMethodDecl method(long flags, Name name, String retType, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, name, qualident(retType), params, code);
    }
    public JCTree.JCMethodDecl method(long flags, String name, JCTree.JCExpression retType, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return method(flags, makeName(name), retType, params, code);
    }
    public JCTree.JCMethodDecl method(long flags, Name name, JCTree.JCExpression retType, List<JCTree.JCVariableDecl> params, JCTree.JCBlock code) {
        return F.MethodDef(F.Modifiers(flags),
                name,
                retType,                                        // return type
                List.< JCTree.JCTypeParameter >nil(),           // type parameters
                params,                                         // parameters
                List.<JCTree.JCExpression>nil(),                // thrown Exceptions
                code,
                null                                            // default value
        );
    }

    public JCTree.JCClassDecl classDecl(long flags, String name,
                                        List<JCTree> members) {
        return classDecl(flags, name, (JCTree.JCExpression)null, List.<JCTree.JCExpression>nil(), members);
    }

    public JCTree.JCClassDecl classDecl(long flags, String name, String extending,
                                        List<JCTree> members) {
        return classDecl(flags, name, extending, List.<JCTree.JCExpression>nil(), members);
    }

    public JCTree.JCClassDecl classDecl(long flags, String name, JCTree.JCExpression extending,
                                        List<JCTree> members) {
        return classDecl(flags, name, extending, List.<JCTree.JCExpression>nil(), members);
    }

    public JCTree.JCClassDecl classDecl(long flags, String name, String extending,
                                        List<JCTree.JCExpression> interfaces,
                                        List<JCTree> members) {
        return classDecl(flags, name, qualident(extending), interfaces, members);
    }

    public JCTree.JCClassDecl classDecl(long flags, String name, JCTree.JCExpression extending,
                                        List<JCTree.JCExpression> interfaces,
                                        List<JCTree> members) {
        return F.ClassDef(F.Modifiers(flags),
                makeName(name),
                List.<JCTree.JCTypeParameter>nil(),
                extending,
                interfaces,
                members);
    }

    public JCTree.JCBlock block(JCTree.JCStatement... statements) {
        return F.Block(0, List.from(statements));
    }

    public JCTree.JCBlock block(List<JCTree.JCStatement> statements) {
        return F.Block(0, statements);
    }

    public List<JCTree.JCVariableDecl> makeParams(JCTree.JCVariableDecl... params) {
        return List.from(params);
    }

    public JCTree.JCStatement assignStmt(String lhs, String rhs) {
        return F.Exec(F.Assign(qualident(lhs), qualident(rhs)));
    }

    public JCTree.JCStatement assignStmt(JCTree.JCExpression lhs, String rhs) {
        return F.Exec(F.Assign(lhs, qualident(rhs)));
    }

    public JCTree.JCStatement assignStmt(String lhs, JCTree.JCExpression rhs) {
        return F.Exec(F.Assign(qualident(lhs), rhs));
    }

    public JCTree.JCArrayTypeTree arrayType(String elemTypeName) {
        return F.TypeArray(qualident(elemTypeName));
    }

    public JCTree.JCExpression arrayAccess(String arrayName, JCTree.JCExpression... indexes) {
        JCTree.JCExpression type = F.Ident(makeName(arrayName));
        for (JCTree.JCExpression expr : indexes) {
            type = F.Indexed(type, expr);
        }
        return type;
    }

    public JCTree.JCMethodInvocation methodCall(String methodName) {
        return methodCall(qualident(methodName), new JCTree.JCExpression[0]);
    }
    public JCTree.JCMethodInvocation methodCall(String methodName, String... arguments) {
        return methodCall(qualident(methodName), arguments);
    }
    public JCTree.JCMethodInvocation methodCall(JCTree.JCExpression methodName) {
        return methodCall(methodName, new String[0]);
    }
    public JCTree.JCMethodInvocation methodCall(JCTree.JCExpression methodName, String... arguments) {
        JCTree.JCExpression[] arr = new JCTree.JCExpression[arguments.length];
        for (int i = 0, argumentsLength = arguments.length; i < argumentsLength; i++) {
            String arg = arguments[i];
            arr[i] = qualident(arg);
        }
        return methodCall(methodName, arr);
    }
    public JCTree.JCMethodInvocation methodCall(String methodName, JCTree.JCExpression... arguments) {
        return methodCall(qualident(methodName), arguments);
    }
    public JCTree.JCMethodInvocation methodCall(JCTree.JCExpression methodName, JCTree.JCExpression... arguments) {
        return F.Apply(List.<JCTree.JCExpression>nil(), methodName, List.from(arguments));
    }

    public JCTree.JCLiteral nullLiteral() {
        return F.Literal(TypeTags.BOT, null);
    }
    public JCTree.JCExpression intType() {
        return F.TypeIdent(TypeTags.INT);
    }
    public JCTree.JCExpression voidType() {
        return F.TypeIdent(TypeTags.VOID);
    }
    public JCTree.JCExpression primitiveType(int type) {
        return F.TypeIdent(type);
    }

    public JCTree.JCExpression parametrizedType(String typeName, String firstTypeArgument) {
        return F.TypeApply(qualident(typeName), List.of(qualident(firstTypeArgument)));
    }

    public boolean isVoid(JCTree.JCExpression expr) {
        return expr instanceof JCTree.JCPrimitiveTypeTree &&
                ((JCTree.JCPrimitiveTypeTree)expr).getPrimitiveTypeKind() == TypeKind.VOID;
    }

    /**
     * Return default value when propagating a pass with a return type
     */
    private JCTree.JCExpression defaultValue(JCTree.JCExpression type) {
        if (type instanceof JCTree.JCPrimitiveTypeTree) {
            TypeKind kind = ((JCTree.JCPrimitiveTypeTree)type).getPrimitiveTypeKind();
            if (kind == TypeKind.INT) {
                return F.Literal(0);
            }
            else if (kind == TypeKind.SHORT) {
                return F.Literal((short)0);
            }
            else if (kind == TypeKind.BOOLEAN) {
                return F.Literal(false);
            }
            else if (kind == TypeKind.BYTE) {
                return F.Literal((byte)0);
            }
            else if (kind == TypeKind.FLOAT) {
                return F.Literal(0f);
            }
            else if (kind == TypeKind.LONG) {
                return F.Literal(0l);
            }
            else if (kind == TypeKind.CHAR) {
                return F.Literal((char)0);
            }
            else if (kind == TypeKind.DOUBLE) {
                return F.Literal(0d);
            }
        }
        return nullLiteral();
    }

    /*
        JCClassDecl interfaceDeclaration(JCModifiers mods, String dc) {
        int pos = S.pos();
        accept(INTERFACE);
        Name name = ident();

        List<JCTypeParameter> typarams = typeParametersOpt();

        List<JCExpression> extending = List.nil();
        if (S.token() == EXTENDS) {
            S.nextToken();
            extending = typeList();
        }
        List<JCTree> defs = classOrInterfaceBody(name, true);
        JCClassDecl result = toP(F.at(pos).ClassDef(
                mods, name, typarams, null, extending, defs));
        attach(result, dc);
        return result;
    }
     */

    ListBuffer<JCTree> treeList() {
        return new ListBuffer<JCTree>();
    }

    // ###############################################################################################
    // Tree visitor for custom members of grammar, simply to fix up the ctor

    JCTree.JCStatement callToInit() {
        return F.Exec(methodCall(GrammarNames._initSymbols));
    }

    boolean foundCtor;
    private boolean checkCtors() {
        JCTree.Visitor vis = new JCTree.Visitor() {
            @Override
            public void visitMethodDef(JCTree.JCMethodDecl that) {
                super.visitMethodDef(that);

                if (that.getName().equals(Names.init)) {
                    // non-default ctor of the grammar
                    foundCtor = true;

                    // add call to this(), if it does not already call another ctor
                    that.body.stats = that.body.stats.prepend(callToInit());
                }
            }

            @Override
            public void visitTree(JCTree that) {
                // do nothing
            }
        };

        foundCtor = false;
        for (JCTree tree : grammar.AdditionalMembers) {
            tree.accept(vis);
        }

        return foundCtor;
    }
}
