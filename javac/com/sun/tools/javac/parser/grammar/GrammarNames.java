package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.jvm.Target;

/**
 * Keep all (or at least most) names in this class.
 * 1. It prevents typos
 * 2. We can easily track and refactor the string occurences
 * 3. Unnamed constants are bad style
 */
public class GrammarNames {
    public static final char syntheticNameChar = Target.JDK1_6.syntheticNameChar();

    // class names
    /**
     * Name of the super class of all NonTerminal classes
     */
    public static final String NonTerminal = "NonTerminal";

    /**
     * Name of the super class of all Terminal classes
     */
    public static final String Terminal = "Terminal";
    
    /**
     * Name of the super class of all Symbol classes
     */
    public static final String Symbol = "Symbol";
    
    public static final String Rule = "Rule";

    public static final String ASTClass = "AST";

    /**
     * Name of the super class of all AST node classes
     */
    public static final String Node = "Node";

    public static final String StartNodeClass = "Start";
    public static final String ASTNodeList = "ASTNodeList";
    public static final String TerminalNode = "TerminalNode";

    public static final String ListType = "ArrayList";

    static String nonTerminalClass(GrammarExpr.NonTerminal var) {
        return var.Name + "SymbolType";
    }

    static String nonTerminalInstance(GrammarExpr.NonTerminal var) {
        return var.Name + "Symbol";
    }

    /**
     * Name of the Rule's singleton class
     */
    static String ruleClass(GrammarExpr.Rule rule) {
        return "Rule_" + rule.Index; 
    }

    static String nonTerminalNodeClass(GrammarExpr.NonTerminal var) {
        return var.Name.toString();
    }

    static String ruleNodeClass(GrammarExpr.NonTerminal var, GrammarExpr.Rule rule) {
        if (rule.Name != null) {
            return rule.Name.toString();
        }
        return var.Name + "_" + rule.Index; 
    }

    
    // variable names
    public static final String NonTerminals = "NonTerminals";
    public static final String Terminals = "Terminals";
    public static final String Id = "Id";
    public static final String index = "index";
    public static final String Index = "Index";
    public static final String Name = "Name";
    public static final String grammarRules = "grammarRules";
    public static final String Root = "Root";
    public static final String symbols = "symbols";
    public static final String TokenName = "TokenName";
    public static final String Parent = "Parent";
    public static final String Token = "Token";
    public static final String node = "node";


    // method names
    public static final String _initSymbols = "_initSymbols";
    public static final String getRule = "getRule";
    public static final String getRules = "getRules";
    public static final String getSymbolCount = "getSymbolCount";
    public static final String getSymbol = "getSymbol";
    public static final String getSymbols = "getSymbols";
    public static final String isLambda = "isLambda";
    public static final String newChild = "newChild";
    public static final String getChild = "getChild";
    public static final String setChild = "setChild";
    
    public static String passPropagationMethod(GrammarExpr.PassDeclaration pass) {
        return "propagate_" + pass.Name;
    }

    public static String astMemberName(GrammarExpr.Symbol sym) {
        return "$" + (sym.Index + 1);
    }

    public static String passMethod(GrammarExpr.PassDeclaration pass) {
        return pass.Name.toString();
    }



    // other
    public static String This = "this";
    public static String Super = "super";
}
