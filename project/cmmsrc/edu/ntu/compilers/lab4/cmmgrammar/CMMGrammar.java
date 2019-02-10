package edu.ntu.compilers.lab4.cmmgrammar;

import edu.ntu.compilers.lab4.parser.*;
import edu.ntu.compilers.lab4.cmmcompiler.*;
import edu.ntu.compilers.lab4.scanner.*;
import edu.ntu.compilers.lab4.tokens.*;
import java.util.ArrayList;

public final class CMMGrammar {
    public static final String GrammarName = "CMMGrammar";
    public final Terminal[] Terminals = new Terminal[]{new Terminal(0, CMMTokenName.OpenCurly), new Terminal(1, CMMTokenName.CloseCurly), new Terminal(2, CMMTokenName.Semicolon), new Terminal(3, CMMTokenName.Identifier), new Terminal(4, CMMTokenName.Comma), new Terminal(5, CMMTokenName.Int), new Terminal(6, CMMTokenName.OpenBrackets), new Terminal(7, CMMTokenName.NumberLiteral), new Terminal(8, CMMTokenName.CloseBrackets), new Terminal(9, CMMTokenName.Equal), new Terminal(10, CMMTokenName.If), new Terminal(11, CMMTokenName.OpenParentheses), new Terminal(12, CMMTokenName.CloseParentheses), new Terminal(13, CMMTokenName.While), new Terminal(14, CMMTokenName.Break), new Terminal(15, CMMTokenName.Continue), new Terminal(16, CMMTokenName.Scan), new Terminal(17, CMMTokenName.Else), new Terminal(18, CMMTokenName.Print), new Terminal(19, CMMTokenName.Println), new Terminal(20, CMMTokenName.StringLiteral), new Terminal(21, CMMTokenName.Plus), new Terminal(22, CMMTokenName.Minus), new Terminal(23, CMMTokenName.Star), new Terminal(24, CMMTokenName.Slash), new Terminal(25, CMMTokenName.LogicalOr), new Terminal(26, CMMTokenName.LogicalAnd), new Terminal(27, CMMTokenName.Exclamation), new Terminal(28, CMMTokenName.Greater), new Terminal(29, CMMTokenName.GreaterEqual), new Terminal(30, CMMTokenName.Less), new Terminal(31, CMMTokenName.LessEqual), new Terminal(32, CMMTokenName.EqualEqual), new Terminal(33, CMMTokenName.NotEqual)};
    public final StartSymbolType StartSymbol = new StartSymbolType();
    public final ProgramSymbolType ProgramSymbol = new ProgramSymbolType();
    public final BlockSymbolType BlockSymbol = new BlockSymbolType();
    public final MultiVarDeclStarSymbolType MultiVarDeclStarSymbol = new MultiVarDeclStarSymbolType();
    public final StmtStarSymbolType StmtStarSymbol = new StmtStarSymbolType();
    public final MultiVarDeclSymbolType MultiVarDeclSymbol = new MultiVarDeclSymbolType();
    public final VarDeclIdListSymbolType VarDeclIdListSymbol = new VarDeclIdListSymbolType();
    public final VarDeclSymbolType VarDeclSymbol = new VarDeclSymbolType();
    public final CommaIdStarSymbolType CommaIdStarSymbol = new CommaIdStarSymbolType();
    public final TypeSymbolType TypeSymbol = new TypeSymbolType();
    public final NumArrayStarSymbolType NumArrayStarSymbol = new NumArrayStarSymbolType();
    public final VarOccurrenceSymbolType VarOccurrenceSymbol = new VarOccurrenceSymbolType();
    public final ArithArrayStarSymbolType ArithArrayStarSymbol = new ArithArrayStarSymbolType();
    public final StmtSymbolType StmtSymbol = new StmtSymbolType();
    public final ElseStmtOptSymbolType ElseStmtOptSymbol = new ElseStmtOptSymbolType();
    public final PrintStatementBaseSymbolType PrintStatementBaseSymbol = new PrintStatementBaseSymbolType();
    public final PrintableExprListSymbolType PrintableExprListSymbol = new PrintableExprListSymbolType();
    public final CommaPrintableExprStarSymbolType CommaPrintableExprStarSymbol = new CommaPrintableExprStarSymbolType();
    public final PrintableExprSymbolType PrintableExprSymbol = new PrintableExprSymbolType();
    public final PrintableExprImplSymbolType PrintableExprImplSymbol = new PrintableExprImplSymbolType();
    public final ScanVarListSymbolType ScanVarListSymbol = new ScanVarListSymbolType();
    public final CommaScanVarStarSymbolType CommaScanVarStarSymbol = new CommaScanVarStarSymbolType();
    public final ScanVarSymbolType ScanVarSymbol = new ScanVarSymbolType();
    public final ArithExprSymbolType ArithExprSymbol = new ArithExprSymbolType();
    public final ArithExprProductSymbolType ArithExprProductSymbol = new ArithExprProductSymbolType();
    public final UnaryArithExprSymbolType UnaryArithExprSymbol = new UnaryArithExprSymbolType();
    public final UnaryArithExprNoPrefixSymbolType UnaryArithExprNoPrefixSymbol = new UnaryArithExprNoPrefixSymbolType();
    public final ArithExprPlusMinusSymbolType ArithExprPlusMinusSymbol = new ArithExprPlusMinusSymbolType();
    public final ArithExprProductMulDivSymbolType ArithExprProductMulDivSymbol = new ArithExprProductMulDivSymbolType();
    public final LogicExprTopSymbolType LogicExprTopSymbol = new LogicExprTopSymbolType();
    public final LogicExprSymbolType LogicExprSymbol = new LogicExprSymbolType();
    public final LogicExprOrRHSSymbolType LogicExprOrRHSSymbol = new LogicExprOrRHSSymbolType();
    public final AndLogicExprSymbolType AndLogicExprSymbol = new AndLogicExprSymbolType();
    public final LogicExprAndRHSSymbolType LogicExprAndRHSSymbol = new LogicExprAndRHSSymbolType();
    public final UnaryLogicExprSymbolType UnaryLogicExprSymbol = new UnaryLogicExprSymbolType();
    public final ArithExprComparisonSuffixSymbolType ArithExprComparisonSuffixSymbol = new ArithExprComparisonSuffixSymbolType();
    public final NonTerminal[] NonTerminals = new NonTerminal[]{StartSymbol, ProgramSymbol, BlockSymbol, MultiVarDeclStarSymbol, StmtStarSymbol, MultiVarDeclSymbol, VarDeclIdListSymbol, VarDeclSymbol, CommaIdStarSymbol, TypeSymbol, NumArrayStarSymbol, VarOccurrenceSymbol, ArithArrayStarSymbol, StmtSymbol, ElseStmtOptSymbol, PrintStatementBaseSymbol, PrintableExprListSymbol, CommaPrintableExprStarSymbol, PrintableExprSymbol, PrintableExprImplSymbol, ScanVarListSymbol, CommaScanVarStarSymbol, ScanVarSymbol, ArithExprSymbol, ArithExprProductSymbol, UnaryArithExprSymbol, UnaryArithExprNoPrefixSymbol, ArithExprPlusMinusSymbol, ArithExprProductMulDivSymbol, LogicExprTopSymbol, LogicExprSymbol, LogicExprOrRHSSymbol, AndLogicExprSymbol, LogicExprAndRHSSymbol, UnaryLogicExprSymbol, ArithExprComparisonSuffixSymbol};
    
    private final void _initSymbols() {
        StartSymbol.grammarRules[0].symbols[0] = ProgramSymbol;
        ProgramSymbol.grammarRules[0].symbols[0] = BlockSymbol;
        BlockSymbol.grammarRules[0].symbols[0] = Terminals[0];
        BlockSymbol.grammarRules[0].symbols[1] = MultiVarDeclStarSymbol;
        BlockSymbol.grammarRules[0].symbols[2] = StmtStarSymbol;
        BlockSymbol.grammarRules[0].symbols[3] = Terminals[1];
        MultiVarDeclStarSymbol.grammarRules[0].symbols[0] = MultiVarDeclSymbol;
        MultiVarDeclStarSymbol.grammarRules[0].symbols[1] = MultiVarDeclStarSymbol;
        StmtStarSymbol.grammarRules[0].symbols[0] = StmtSymbol;
        StmtStarSymbol.grammarRules[0].symbols[1] = StmtStarSymbol;
        MultiVarDeclSymbol.grammarRules[0].symbols[0] = TypeSymbol;
        MultiVarDeclSymbol.grammarRules[0].symbols[1] = VarDeclIdListSymbol;
        MultiVarDeclSymbol.grammarRules[0].symbols[2] = Terminals[2];
        VarDeclIdListSymbol.grammarRules[0].symbols[0] = VarDeclSymbol;
        VarDeclIdListSymbol.grammarRules[0].symbols[1] = CommaIdStarSymbol;
        VarDeclSymbol.grammarRules[0].symbols[0] = Terminals[3];
        CommaIdStarSymbol.grammarRules[0].symbols[0] = Terminals[4];
        CommaIdStarSymbol.grammarRules[0].symbols[1] = VarDeclIdListSymbol;
        TypeSymbol.grammarRules[0].symbols[0] = Terminals[5];
        TypeSymbol.grammarRules[0].symbols[1] = NumArrayStarSymbol;
        NumArrayStarSymbol.grammarRules[0].symbols[0] = Terminals[6];
        NumArrayStarSymbol.grammarRules[0].symbols[1] = Terminals[7];
        NumArrayStarSymbol.grammarRules[0].symbols[2] = Terminals[8];
        NumArrayStarSymbol.grammarRules[0].symbols[3] = NumArrayStarSymbol;
        VarOccurrenceSymbol.grammarRules[0].symbols[0] = Terminals[3];
        VarOccurrenceSymbol.grammarRules[0].symbols[1] = ArithArrayStarSymbol;
        ArithArrayStarSymbol.grammarRules[0].symbols[0] = Terminals[6];
        ArithArrayStarSymbol.grammarRules[0].symbols[1] = ArithExprSymbol;
        ArithArrayStarSymbol.grammarRules[0].symbols[2] = Terminals[8];
        ArithArrayStarSymbol.grammarRules[0].symbols[3] = ArithArrayStarSymbol;
        StmtSymbol.grammarRules[0].symbols[0] = VarOccurrenceSymbol;
        StmtSymbol.grammarRules[0].symbols[1] = Terminals[9];
        StmtSymbol.grammarRules[0].symbols[2] = ArithExprSymbol;
        StmtSymbol.grammarRules[0].symbols[3] = Terminals[2];
        StmtSymbol.grammarRules[1].symbols[0] = Terminals[10];
        StmtSymbol.grammarRules[1].symbols[1] = Terminals[11];
        StmtSymbol.grammarRules[1].symbols[2] = LogicExprTopSymbol;
        StmtSymbol.grammarRules[1].symbols[3] = Terminals[12];
        StmtSymbol.grammarRules[1].symbols[4] = BlockSymbol;
        StmtSymbol.grammarRules[1].symbols[5] = ElseStmtOptSymbol;
        StmtSymbol.grammarRules[2].symbols[0] = Terminals[13];
        StmtSymbol.grammarRules[2].symbols[1] = Terminals[11];
        StmtSymbol.grammarRules[2].symbols[2] = LogicExprTopSymbol;
        StmtSymbol.grammarRules[2].symbols[3] = Terminals[12];
        StmtSymbol.grammarRules[2].symbols[4] = BlockSymbol;
        StmtSymbol.grammarRules[3].symbols[0] = Terminals[14];
        StmtSymbol.grammarRules[3].symbols[1] = Terminals[2];
        StmtSymbol.grammarRules[4].symbols[0] = Terminals[15];
        StmtSymbol.grammarRules[4].symbols[1] = Terminals[2];
        StmtSymbol.grammarRules[5].symbols[0] = Terminals[16];
        StmtSymbol.grammarRules[5].symbols[1] = Terminals[11];
        StmtSymbol.grammarRules[5].symbols[2] = ScanVarListSymbol;
        StmtSymbol.grammarRules[5].symbols[3] = Terminals[12];
        StmtSymbol.grammarRules[5].symbols[4] = Terminals[2];
        StmtSymbol.grammarRules[6].symbols[0] = PrintStatementBaseSymbol;
        StmtSymbol.grammarRules[7].symbols[0] = BlockSymbol;
        ElseStmtOptSymbol.grammarRules[0].symbols[0] = Terminals[17];
        ElseStmtOptSymbol.grammarRules[0].symbols[1] = BlockSymbol;
        PrintStatementBaseSymbol.grammarRules[0].symbols[0] = Terminals[18];
        PrintStatementBaseSymbol.grammarRules[0].symbols[1] = Terminals[11];
        PrintStatementBaseSymbol.grammarRules[0].symbols[2] = PrintableExprListSymbol;
        PrintStatementBaseSymbol.grammarRules[0].symbols[3] = Terminals[12];
        PrintStatementBaseSymbol.grammarRules[0].symbols[4] = Terminals[2];
        PrintStatementBaseSymbol.grammarRules[1].symbols[0] = Terminals[19];
        PrintStatementBaseSymbol.grammarRules[1].symbols[1] = Terminals[11];
        PrintStatementBaseSymbol.grammarRules[1].symbols[2] = PrintableExprListSymbol;
        PrintStatementBaseSymbol.grammarRules[1].symbols[3] = Terminals[12];
        PrintStatementBaseSymbol.grammarRules[1].symbols[4] = Terminals[2];
        PrintableExprListSymbol.grammarRules[0].symbols[0] = PrintableExprSymbol;
        PrintableExprListSymbol.grammarRules[0].symbols[1] = CommaPrintableExprStarSymbol;
        CommaPrintableExprStarSymbol.grammarRules[0].symbols[0] = Terminals[4];
        CommaPrintableExprStarSymbol.grammarRules[0].symbols[1] = PrintableExprListSymbol;
        PrintableExprSymbol.grammarRules[0].symbols[0] = PrintableExprImplSymbol;
        PrintableExprImplSymbol.grammarRules[0].symbols[0] = Terminals[20];
        PrintableExprImplSymbol.grammarRules[1].symbols[0] = ArithExprSymbol;
        ScanVarListSymbol.grammarRules[0].symbols[0] = ScanVarSymbol;
        ScanVarListSymbol.grammarRules[0].symbols[1] = CommaScanVarStarSymbol;
        CommaScanVarStarSymbol.grammarRules[0].symbols[0] = Terminals[4];
        CommaScanVarStarSymbol.grammarRules[0].symbols[1] = ScanVarSymbol;
        CommaScanVarStarSymbol.grammarRules[0].symbols[2] = CommaScanVarStarSymbol;
        ScanVarSymbol.grammarRules[0].symbols[0] = VarOccurrenceSymbol;
        ArithExprSymbol.grammarRules[0].symbols[0] = ArithExprProductSymbol;
        ArithExprSymbol.grammarRules[0].symbols[1] = ArithExprPlusMinusSymbol;
        ArithExprProductSymbol.grammarRules[0].symbols[0] = UnaryArithExprSymbol;
        ArithExprProductSymbol.grammarRules[0].symbols[1] = ArithExprProductMulDivSymbol;
        UnaryArithExprSymbol.grammarRules[0].symbols[0] = Terminals[21];
        UnaryArithExprSymbol.grammarRules[0].symbols[1] = UnaryArithExprNoPrefixSymbol;
        UnaryArithExprSymbol.grammarRules[1].symbols[0] = Terminals[22];
        UnaryArithExprSymbol.grammarRules[1].symbols[1] = UnaryArithExprNoPrefixSymbol;
        UnaryArithExprSymbol.grammarRules[2].symbols[0] = UnaryArithExprNoPrefixSymbol;
        UnaryArithExprNoPrefixSymbol.grammarRules[0].symbols[0] = VarOccurrenceSymbol;
        UnaryArithExprNoPrefixSymbol.grammarRules[1].symbols[0] = Terminals[7];
        UnaryArithExprNoPrefixSymbol.grammarRules[2].symbols[0] = Terminals[11];
        UnaryArithExprNoPrefixSymbol.grammarRules[2].symbols[1] = ArithExprSymbol;
        UnaryArithExprNoPrefixSymbol.grammarRules[2].symbols[2] = Terminals[12];
        ArithExprPlusMinusSymbol.grammarRules[0].symbols[0] = Terminals[21];
        ArithExprPlusMinusSymbol.grammarRules[0].symbols[1] = ArithExprProductSymbol;
        ArithExprPlusMinusSymbol.grammarRules[0].symbols[2] = ArithExprPlusMinusSymbol;
        ArithExprPlusMinusSymbol.grammarRules[1].symbols[0] = Terminals[22];
        ArithExprPlusMinusSymbol.grammarRules[1].symbols[1] = ArithExprProductSymbol;
        ArithExprPlusMinusSymbol.grammarRules[1].symbols[2] = ArithExprPlusMinusSymbol;
        ArithExprProductMulDivSymbol.grammarRules[0].symbols[0] = Terminals[23];
        ArithExprProductMulDivSymbol.grammarRules[0].symbols[1] = UnaryArithExprSymbol;
        ArithExprProductMulDivSymbol.grammarRules[0].symbols[2] = ArithExprProductMulDivSymbol;
        ArithExprProductMulDivSymbol.grammarRules[1].symbols[0] = Terminals[24];
        ArithExprProductMulDivSymbol.grammarRules[1].symbols[1] = UnaryArithExprSymbol;
        ArithExprProductMulDivSymbol.grammarRules[1].symbols[2] = ArithExprProductMulDivSymbol;
        LogicExprTopSymbol.grammarRules[0].symbols[0] = LogicExprSymbol;
        LogicExprSymbol.grammarRules[0].symbols[0] = AndLogicExprSymbol;
        LogicExprSymbol.grammarRules[0].symbols[1] = LogicExprOrRHSSymbol;
        LogicExprOrRHSSymbol.grammarRules[0].symbols[0] = Terminals[25];
        LogicExprOrRHSSymbol.grammarRules[0].symbols[1] = LogicExprSymbol;
        AndLogicExprSymbol.grammarRules[0].symbols[0] = UnaryLogicExprSymbol;
        AndLogicExprSymbol.grammarRules[0].symbols[1] = LogicExprAndRHSSymbol;
        LogicExprAndRHSSymbol.grammarRules[0].symbols[0] = Terminals[26];
        LogicExprAndRHSSymbol.grammarRules[0].symbols[1] = AndLogicExprSymbol;
        UnaryLogicExprSymbol.grammarRules[0].symbols[0] = ArithExprSymbol;
        UnaryLogicExprSymbol.grammarRules[0].symbols[1] = ArithExprComparisonSuffixSymbol;
        UnaryLogicExprSymbol.grammarRules[1].symbols[0] = Terminals[6];
        UnaryLogicExprSymbol.grammarRules[1].symbols[1] = LogicExprSymbol;
        UnaryLogicExprSymbol.grammarRules[1].symbols[2] = Terminals[8];
        UnaryLogicExprSymbol.grammarRules[2].symbols[0] = Terminals[27];
        UnaryLogicExprSymbol.grammarRules[2].symbols[1] = UnaryLogicExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[0].symbols[0] = Terminals[28];
        ArithExprComparisonSuffixSymbol.grammarRules[0].symbols[1] = ArithExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[1].symbols[0] = Terminals[29];
        ArithExprComparisonSuffixSymbol.grammarRules[1].symbols[1] = ArithExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[2].symbols[0] = Terminals[30];
        ArithExprComparisonSuffixSymbol.grammarRules[2].symbols[1] = ArithExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[3].symbols[0] = Terminals[31];
        ArithExprComparisonSuffixSymbol.grammarRules[3].symbols[1] = ArithExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[4].symbols[0] = Terminals[32];
        ArithExprComparisonSuffixSymbol.grammarRules[4].symbols[1] = ArithExprSymbol;
        ArithExprComparisonSuffixSymbol.grammarRules[5].symbols[0] = Terminals[33];
        ArithExprComparisonSuffixSymbol.grammarRules[5].symbols[1] = ArithExprSymbol;
    }
    
    public AST createAST() {
        return new AST();
    }
    
    public static abstract class Symbol {
    }
    
    public static final class Terminal extends Symbol {
        public final int Id;
        public final CMMTokenName TokenName;
        
        public Terminal(int id, CMMTokenName tokenName) {
            Id = id;
            TokenName = tokenName;
        }
    }
    
    public static abstract class NonTerminal extends Symbol {
        public final int Id;
        public final String Name;
        protected Rule[] grammarRules;
        
        protected NonTerminal(int id, String name) {
            Id = id;
            Name = name;
        }
        
        public Rule getRule(int i) {
            return grammarRules[i];
        }
        
        public java.util.List<Rule> getRules() {
            return java.util.Arrays.asList(grammarRules);
        }
    }
    
    public static abstract class Rule {
        public final NonTerminal NonTerminal;
        private Symbol[] symbols;
        public final int Index;
        
        protected Rule(NonTerminal nonTerminal, Symbol[] symbols, int index) {
            NonTerminal = nonTerminal;
            this.symbols = symbols;
            Index = index;
        }
        
        public int getSymbolCount() {
            return symbols.length;
        }
        
        public Symbol getSymbol(int i) {
            return symbols[i];
        }
        
        public java.util.List<Symbol> getSymbols() {
            return java.util.Arrays.asList(symbols);
        }
        
        abstract Node newChild(int index, Node node);
    }
    
    public final class AST {
        public final Start_0 Root = new Start_0(0, null);
        
        protected AST() {
        }
    }
    
    public abstract class Node {
        public final Node Parent;
        public final Token Token;
        public final Rule Rule;
        public final int Index;
        
        protected Node(int index, Node parent, Token token) {
            Index = index;
            Parent = parent;
            Token = token;
            Rule = null;
        }
        
        protected Node(int index, Node parent, Rule rule) {
            Index = index;
            Parent = parent;
            Token = null;
            Rule = rule;
        }
        
        public boolean isLambda() {
            return Rule != null && Rule.getSymbolCount() == 0;
        }
        
        public Node newChild(int index, Rule childRule) {
            Node node = childRule.newChild(index, this);
            setChild(node);
            return node;
        }
        
        public TerminalNode newChild(int index, Terminal terminal, Token token) {
            TerminalNode node = new TerminalNode(this, terminal, token, index);
            setChild(node);
            return node;
        }
        
        public Node getChild(int index) {
            return null;
        }
        
        public void setChild(Node node) {
        }
        
        public int index() {
            return Index;
        }
        
        public void propagate_prep0() {
        }
        
        public void propagate_gen() {
        }
        
        public Stmt getNextStmt() {
            if (Parent == null) {
                return null;
            }
            return Parent.getFirstStmtToTheRightOf(index());
        }
        
        public Stmt getFirstStmtToTheRightOf(int index) {
            index++;
            if (this instanceof TerminalNode || Rule.getSymbolCount() <= index) {
                if (Parent == null) {
                    return null;
                }
                return Parent.getFirstStmtToTheRightOf(index());
            }
            Node child = getChild(index);
            if (child instanceof Stmt) {
                return (Stmt)child;
            }
            return child.getFirstStmtToTheRightOf(-1);
        }
    }
    
    public final class TerminalNode extends Node {
        public final Terminal Terminal;
        
        protected TerminalNode(Node parent, Terminal terminal, Token token, int index) {
            super(index, parent, token);
            Terminal = terminal;
        }
    }
    
    public final class StartSymbolType extends NonTerminal {
        
        private StartSymbolType() {
            super(0, "Start");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(StartSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Start_0(index, Parent);
            }
        }
    }
    
    public final class ProgramSymbolType extends NonTerminal {
        
        private ProgramSymbolType() {
            super(1, "Program");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ProgramSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Program_0(index, Parent);
            }
        }
    }
    
    public final class BlockSymbolType extends NonTerminal {
        
        private BlockSymbolType() {
            super(2, "Block");
            grammarRules = new Rule[]{new Rule_0(new Symbol[4])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(BlockSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Block_0(index, Parent);
            }
        }
    }
    
    public final class MultiVarDeclStarSymbolType extends NonTerminal {
        
        private MultiVarDeclStarSymbolType() {
            super(3, "MultiVarDeclStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(MultiVarDeclStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new MultiVarDeclStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(MultiVarDeclStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new MultiVarDeclStar_1(index, Parent);
            }
        }
    }
    
    public final class StmtStarSymbolType extends NonTerminal {
        
        private StmtStarSymbolType() {
            super(4, "StmtStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(StmtStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new StmtStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(StmtStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new StmtStar_1(index, Parent);
            }
        }
    }
    
    public final class MultiVarDeclSymbolType extends NonTerminal {
        
        private MultiVarDeclSymbolType() {
            super(5, "MultiVarDecl");
            grammarRules = new Rule[]{new Rule_0(new Symbol[3])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(MultiVarDeclSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new MultiVarDecl_0(index, Parent);
            }
        }
    }
    
    public final class VarDeclIdListSymbolType extends NonTerminal {
        
        private VarDeclIdListSymbolType() {
            super(6, "VarDeclIdList");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(VarDeclIdListSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new VarDeclIdList_0(index, Parent);
            }
        }
    }
    
    public final class VarDeclSymbolType extends NonTerminal {
        
        private VarDeclSymbolType() {
            super(7, "VarDecl");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(VarDeclSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new VarDecl_0(index, Parent);
            }
        }
    }
    
    public final class CommaIdStarSymbolType extends NonTerminal {
        
        private CommaIdStarSymbolType() {
            super(8, "CommaIdStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(CommaIdStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaIdStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(CommaIdStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaIdStar_1(index, Parent);
            }
        }
    }
    
    public final class TypeSymbolType extends NonTerminal {
        
        private TypeSymbolType() {
            super(9, "Type");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(TypeSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Type_0(index, Parent);
            }
        }
    }
    
    public final class NumArrayStarSymbolType extends NonTerminal {
        
        private NumArrayStarSymbolType() {
            super(10, "NumArrayStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[4]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(NumArrayStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new NumArrayStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(NumArrayStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new NumArrayStar_1(index, Parent);
            }
        }
    }
    
    public final class VarOccurrenceSymbolType extends NonTerminal {
        
        private VarOccurrenceSymbolType() {
            super(11, "VarOccurrence");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(VarOccurrenceSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new VarOccurrence_0(index, Parent);
            }
        }
    }
    
    public final class ArithArrayStarSymbolType extends NonTerminal {
        
        private ArithArrayStarSymbolType() {
            super(12, "ArithArrayStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[4]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithArrayStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithArrayStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(ArithArrayStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithArrayStar_1(index, Parent);
            }
        }
    }
    
    public final class StmtSymbolType extends NonTerminal {
        
        private StmtSymbolType() {
            super(13, "Stmt");
            grammarRules = new Rule[]{new Rule_0(new Symbol[4]), new Rule_1(new Symbol[6]), new Rule_2(new Symbol[5]), new Rule_3(new Symbol[2]), new Rule_4(new Symbol[2]), new Rule_5(new Symbol[5]), new Rule_6(new Symbol[1]), new Rule_7(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Assignment(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new IfStmt(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new WhileStmt(index, Parent);
            }
        }
        
        public final class Rule_3 extends Rule {
            
            protected Rule_3(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 3);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Stmt_3(index, Parent);
            }
        }
        
        public final class Rule_4 extends Rule {
            
            protected Rule_4(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 4);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Stmt_4(index, Parent);
            }
        }
        
        public final class Rule_5 extends Rule {
            
            protected Rule_5(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 5);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ScanStmt(index, Parent);
            }
        }
        
        public final class Rule_6 extends Rule {
            
            protected Rule_6(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 6);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Stmt_6(index, Parent);
            }
        }
        
        public final class Rule_7 extends Rule {
            
            protected Rule_7(Symbol[] symbols) {
                super(StmtSymbolType.this, symbols, 7);
            }
            
            public Node newChild(int index, Node Parent) {
                return new Stmt_7(index, Parent);
            }
        }
    }
    
    public final class ElseStmtOptSymbolType extends NonTerminal {
        
        private ElseStmtOptSymbolType() {
            super(14, "ElseStmtOpt");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ElseStmtOptSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ElseStmtOpt_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(ElseStmtOptSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ElseStmtOpt_1(index, Parent);
            }
        }
    }
    
    public final class PrintStatementBaseSymbolType extends NonTerminal {
        
        private PrintStatementBaseSymbolType() {
            super(15, "PrintStatementBase");
            grammarRules = new Rule[]{new Rule_0(new Symbol[5]), new Rule_1(new Symbol[5])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(PrintStatementBaseSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintStmt(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(PrintStatementBaseSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintlnStmt(index, Parent);
            }
        }
    }
    
    public final class PrintableExprListSymbolType extends NonTerminal {
        
        private PrintableExprListSymbolType() {
            super(16, "PrintableExprList");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(PrintableExprListSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintableExprList_0(index, Parent);
            }
        }
    }
    
    public final class CommaPrintableExprStarSymbolType extends NonTerminal {
        
        private CommaPrintableExprStarSymbolType() {
            super(17, "CommaPrintableExprStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(CommaPrintableExprStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaPrintableExprStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(CommaPrintableExprStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaPrintableExprStar_1(index, Parent);
            }
        }
    }
    
    public final class PrintableExprSymbolType extends NonTerminal {
        
        private PrintableExprSymbolType() {
            super(18, "PrintableExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(PrintableExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintableExpr_0(index, Parent);
            }
        }
    }
    
    public final class PrintableExprImplSymbolType extends NonTerminal {
        
        private PrintableExprImplSymbolType() {
            super(19, "PrintableExprImpl");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1]), new Rule_1(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(PrintableExprImplSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintableString(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(PrintableExprImplSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new PrintableInt(index, Parent);
            }
        }
    }
    
    public final class ScanVarListSymbolType extends NonTerminal {
        
        private ScanVarListSymbolType() {
            super(20, "ScanVarList");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ScanVarListSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ScanVarList_0(index, Parent);
            }
        }
    }
    
    public final class CommaScanVarStarSymbolType extends NonTerminal {
        
        private CommaScanVarStarSymbolType() {
            super(21, "CommaScanVarStar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[3]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(CommaScanVarStarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaScanVarStar_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(CommaScanVarStarSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new CommaScanVarStar_1(index, Parent);
            }
        }
    }
    
    public final class ScanVarSymbolType extends NonTerminal {
        
        private ScanVarSymbolType() {
            super(22, "ScanVar");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ScanVarSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ScanVar_0(index, Parent);
            }
        }
    }
    
    public final class ArithExprSymbolType extends NonTerminal {
        
        private ArithExprSymbolType() {
            super(23, "ArithExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExpr_0(index, Parent);
            }
        }
    }
    
    public final class ArithExprProductSymbolType extends NonTerminal {
        
        private ArithExprProductSymbolType() {
            super(24, "ArithExprProduct");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithExprProductSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprProduct_0(index, Parent);
            }
        }
    }
    
    public final class UnaryArithExprSymbolType extends NonTerminal {
        
        private UnaryArithExprSymbolType() {
            super(25, "UnaryArithExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[2]), new Rule_2(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(UnaryArithExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExpr_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(UnaryArithExprSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExpr_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(UnaryArithExprSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExpr_2(index, Parent);
            }
        }
    }
    
    public final class UnaryArithExprNoPrefixSymbolType extends NonTerminal {
        
        private UnaryArithExprNoPrefixSymbolType() {
            super(26, "UnaryArithExprNoPrefix");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1]), new Rule_1(new Symbol[1]), new Rule_2(new Symbol[3])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(UnaryArithExprNoPrefixSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExprNoPrefix_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(UnaryArithExprNoPrefixSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExprNoPrefix_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(UnaryArithExprNoPrefixSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryArithExprNoPrefix_2(index, Parent);
            }
        }
    }
    
    public final class ArithExprPlusMinusSymbolType extends NonTerminal {
        
        private ArithExprPlusMinusSymbolType() {
            super(27, "ArithExprPlusMinus");
            grammarRules = new Rule[]{new Rule_0(new Symbol[3]), new Rule_1(new Symbol[3]), new Rule_2(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithExprPlusMinusSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprPlusMinus_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(ArithExprPlusMinusSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprPlusMinus_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(ArithExprPlusMinusSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprPlusMinus_2(index, Parent);
            }
        }
    }
    
    public final class ArithExprProductMulDivSymbolType extends NonTerminal {
        
        private ArithExprProductMulDivSymbolType() {
            super(28, "ArithExprProductMulDiv");
            grammarRules = new Rule[]{new Rule_0(new Symbol[3]), new Rule_1(new Symbol[3]), new Rule_2(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithExprProductMulDivSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprProductMulDiv_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(ArithExprProductMulDivSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprProductMulDiv_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(ArithExprProductMulDivSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprProductMulDiv_2(index, Parent);
            }
        }
    }
    
    public final class LogicExprTopSymbolType extends NonTerminal {
        
        private LogicExprTopSymbolType() {
            super(29, "LogicExprTop");
            grammarRules = new Rule[]{new Rule_0(new Symbol[1])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(LogicExprTopSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExprTop_0(index, Parent);
            }
        }
    }
    
    public final class LogicExprSymbolType extends NonTerminal {
        
        private LogicExprSymbolType() {
            super(30, "LogicExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(LogicExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExpr_0(index, Parent);
            }
        }
    }
    
    public final class LogicExprOrRHSSymbolType extends NonTerminal {
        
        private LogicExprOrRHSSymbolType() {
            super(31, "LogicExprOrRHS");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(LogicExprOrRHSSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExprOrRHS_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(LogicExprOrRHSSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExprOrRHS_1(index, Parent);
            }
        }
    }
    
    public final class AndLogicExprSymbolType extends NonTerminal {
        
        private AndLogicExprSymbolType() {
            super(32, "AndLogicExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(AndLogicExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new AndLogicExpr_0(index, Parent);
            }
        }
    }
    
    public final class LogicExprAndRHSSymbolType extends NonTerminal {
        
        private LogicExprAndRHSSymbolType() {
            super(33, "LogicExprAndRHS");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[0])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(LogicExprAndRHSSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExprAndRHS_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(LogicExprAndRHSSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new LogicExprAndRHS_1(index, Parent);
            }
        }
    }
    
    public final class UnaryLogicExprSymbolType extends NonTerminal {
        
        private UnaryLogicExprSymbolType() {
            super(34, "UnaryLogicExpr");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[3]), new Rule_2(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(UnaryLogicExprSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryLogicExpr_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(UnaryLogicExprSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryLogicExpr_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(UnaryLogicExprSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new UnaryLogicExpr_2(index, Parent);
            }
        }
    }
    
    public final class ArithExprComparisonSuffixSymbolType extends NonTerminal {
        
        private ArithExprComparisonSuffixSymbolType() {
            super(35, "ArithExprComparisonSuffix");
            grammarRules = new Rule[]{new Rule_0(new Symbol[2]), new Rule_1(new Symbol[2]), new Rule_2(new Symbol[2]), new Rule_3(new Symbol[2]), new Rule_4(new Symbol[2]), new Rule_5(new Symbol[2])};
        }
        
        public final class Rule_0 extends Rule {
            
            protected Rule_0(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 0);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_0(index, Parent);
            }
        }
        
        public final class Rule_1 extends Rule {
            
            protected Rule_1(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 1);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_1(index, Parent);
            }
        }
        
        public final class Rule_2 extends Rule {
            
            protected Rule_2(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 2);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_2(index, Parent);
            }
        }
        
        public final class Rule_3 extends Rule {
            
            protected Rule_3(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 3);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_3(index, Parent);
            }
        }
        
        public final class Rule_4 extends Rule {
            
            protected Rule_4(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 4);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_4(index, Parent);
            }
        }
        
        public final class Rule_5 extends Rule {
            
            protected Rule_5(Symbol[] symbols) {
                super(ArithExprComparisonSuffixSymbolType.this, symbols, 5);
            }
            
            public Node newChild(int index, Node Parent) {
                return new ArithExprComparisonSuffix_5(index, Parent);
            }
        }
    }
    
    public abstract class Start extends Node {
        
        protected Start(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class Start_0 extends Start {
        public Program $1;
        
        protected Start_0(int index, Node parent) {
            super(index, parent, StartSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (Program)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class Program extends Node {
        
        protected Program(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class Program_0 extends Program {
        public Block $1;
        
        protected Program_0(int index, Node parent) {
            super(index, parent, ProgramSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (Block)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            if (Code.HasScanStmt) {
                Code.Gen.initScanner();
            }
            $1.gen();
            Code.Gen.returnn();
        }
    }
    
    public abstract class Block extends Node {
        
        protected Block(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
        public Scope Scope;
    }
    
    public final class Block_0 extends Block {
        public TerminalNode $1;
        public MultiVarDeclStar $2;
        public StmtStar $3;
        public TerminalNode $4;
        
        protected Block_0(int index, Node parent) {
            super(index, parent, BlockSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (MultiVarDeclStar)node;
                break;
            
            case 2: 
                $3 = (StmtStar)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            Scope = Context.newScope(this);
            propagate_prep0();
            Context.leaveScope();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            Context.enterScope(Scope);
            propagate_gen();
            Context.leaveScope();
        }
    }
    
    public abstract class MultiVarDeclStar extends Node {
        
        protected MultiVarDeclStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class MultiVarDeclStar_0 extends MultiVarDeclStar {
        public MultiVarDecl $1;
        public MultiVarDeclStar $2;
        
        protected MultiVarDeclStar_0(int index, Node parent) {
            super(index, parent, MultiVarDeclStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (MultiVarDecl)node;
                break;
            
            case 1: 
                $2 = (MultiVarDeclStar)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class MultiVarDeclStar_1 extends MultiVarDeclStar {
        
        protected MultiVarDeclStar_1(int index, Node parent) {
            super(index, parent, MultiVarDeclStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class StmtStar extends Node {
        
        protected StmtStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class StmtStar_0 extends StmtStar {
        public Stmt $1;
        public StmtStar $2;
        
        protected StmtStar_0(int index, Node parent) {
            super(index, parent, StmtStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (Stmt)node;
                break;
            
            case 1: 
                $2 = (StmtStar)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class StmtStar_1 extends StmtStar {
        
        protected StmtStar_1(int index, Node parent) {
            super(index, parent, StmtStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class MultiVarDecl extends Node {
        
        protected MultiVarDecl(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class MultiVarDecl_0 extends MultiVarDecl {
        public Type $1;
        public VarDeclIdList $2;
        public TerminalNode $3;
        
        protected MultiVarDecl_0(int index, Node parent) {
            super(index, parent, MultiVarDeclSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (Type)node;
                break;
            
            case 1: 
                $2 = (VarDeclIdList)node;
                break;
            
            case 2: 
                $3 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void prep0() {
            $1.prep0();
            $2.prep0($1);
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class VarDeclIdList extends Node {
        
        protected VarDeclIdList(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0(Type type);
        
        public abstract void gen();
    }
    
    public final class VarDeclIdList_0 extends VarDeclIdList {
        public VarDecl $1;
        public CommaIdStar $2;
        
        protected VarDeclIdList_0(int index, Node parent) {
            super(index, parent, VarDeclIdListSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (VarDecl)node;
                break;
            
            case 1: 
                $2 = (CommaIdStar)node;
                break;
            
            }
        }
        
        public void prep0(Type type) {
            $1.prep0(type);
            $2.prep0(type);
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class VarDecl extends Node {
        
        protected VarDecl(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0(Type type);
        
        public abstract void gen();
        public Scope Scope;
        public Type Type;
        public String Name;
        public int Id;
        
        public String toString() {
            return Name;
        }
    }
    
    public final class VarDecl_0 extends VarDecl {
        public TerminalNode $1;
        
        protected VarDecl_0(int index, Node parent) {
            super(index, parent, VarDeclSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0(Type type) {
            Scope = Context.getCurrentScope();
            Type = type;
            Name = $1.Token.stringValue();
            Id = Scope.declareVar(this);
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            if (Type.isArray()) {
                Code.Gen.newArray(this);
            }
        }
    }
    
    public abstract class CommaIdStar extends Node {
        
        protected CommaIdStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0(Type type);
        
        public abstract void gen();
    }
    
    public final class CommaIdStar_0 extends CommaIdStar {
        public TerminalNode $1;
        public VarDeclIdList $2;
        
        protected CommaIdStar_0(int index, Node parent) {
            super(index, parent, CommaIdStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (VarDeclIdList)node;
                break;
            
            }
        }
        
        public void prep0(Type type) {
            $2.prep0(type);
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class CommaIdStar_1 extends CommaIdStar {
        
        protected CommaIdStar_1(int index, Node parent) {
            super(index, parent, CommaIdStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0(Type type) {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class Type extends Node {
        
        protected Type(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
        public BaseType BaseType;
        public final IntegerList ArrayDimensions = new IntegerList();
        
        public boolean isArray() {
            return ArrayDimensions.size() != 0;
        }
    }
    
    public final class Type_0 extends Type {
        public TerminalNode $1;
        public NumArrayStar $2;
        
        protected Type_0(int index, Node parent) {
            super(index, parent, TypeSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (NumArrayStar)node;
                break;
            
            }
        }
        
        public void prep0() {
            BaseType = BaseType.Integer;
            $2.prep0(ArrayDimensions);
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class NumArrayStar extends Node {
        
        protected NumArrayStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0(IntegerList dims);
        
        public abstract void gen();
    }
    
    public final class NumArrayStar_0 extends NumArrayStar {
        public TerminalNode $1;
        public TerminalNode $2;
        public TerminalNode $3;
        public NumArrayStar $4;
        
        protected NumArrayStar_0(int index, Node parent) {
            super(index, parent, NumArrayStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (TerminalNode)node;
                break;
            
            case 3: 
                $4 = (NumArrayStar)node;
                break;
            
            }
        }
        
        public void prep0(IntegerList dims) {
            dims.add($2.Token.intValue());
            $4.prep0(dims);
        }
        
        public void propagate_gen() {
            $4.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class NumArrayStar_1 extends NumArrayStar {
        
        protected NumArrayStar_1(int index, Node parent) {
            super(index, parent, NumArrayStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0(IntegerList dims) {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class VarOccurrence extends Node {
        
        protected VarOccurrence(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
        public String Name;
        public final ArithExprList ArrayDimensions = new ArithExprList();
        public VarDecl Decl;
        
        public boolean isArray() {
            return ArrayDimensions.size() != 0;
        }
    }
    
    public final class VarOccurrence_0 extends VarOccurrence {
        public TerminalNode $1;
        public ArithArrayStar $2;
        
        protected VarOccurrence_0(int index, Node parent) {
            super(index, parent, VarOccurrenceSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithArrayStar)node;
                break;
            
            }
        }
        
        public void prep0() {
            Name = $1.Token.stringValue();
            $2.prep0(ArrayDimensions);
            Context.getCurrentScope().lookupVariable(this);
            assert (Decl != null);
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            assert (Name != null && Decl != null);
            if (isArray()) {
                Code.Gen.aload(this);
                boolean isStore = Code.IsStore;
                Code.IsStore = false;
                $2.gen();
                Code.IsStore = isStore;
                if (!Code.IsStore) {
                    Code.Gen.iaload(this);
                }
            } else if (!Code.IsStore) {
                Code.Gen.iload(this);
            } else {
            }
        }
    }
    
    public abstract class ArithArrayStar extends Node {
        
        protected ArithArrayStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0(ArithExprList dims);
        
        public abstract void gen();
    }
    
    public final class ArithArrayStar_0 extends ArithArrayStar {
        public TerminalNode $1;
        public ArithExpr $2;
        public TerminalNode $3;
        public ArithArrayStar $4;
        
        protected ArithArrayStar_0(int index, Node parent) {
            super(index, parent, ArithArrayStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            case 2: 
                $3 = (TerminalNode)node;
                break;
            
            case 3: 
                $4 = (ArithArrayStar)node;
                break;
            
            }
        }
        
        public void prep0(ArithExprList dims) {
            $2.prep0();
            dims.add($2);
            $4.prep0(dims);
        }
        
        public void propagate_gen() {
            $2.gen();
            $4.gen();
        }
        
        public void gen() {
            $2.gen();
            if (!$4.isLambda()) {
                Code.Gen.aaload();
                $4.gen();
            }
        }
    }
    
    public final class ArithArrayStar_1 extends ArithArrayStar {
        
        protected ArithArrayStar_1(int index, Node parent) {
            super(index, parent, ArithArrayStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0(ArithExprList dims) {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class Stmt extends Node {
        
        protected Stmt(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class Assignment extends Stmt {
        public VarOccurrence $1;
        public TerminalNode $2;
        public ArithExpr $3;
        public TerminalNode $4;
        
        protected Assignment(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (VarOccurrence)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (ArithExpr)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $3.gen();
        }
        
        public void gen() {
            boolean isStore = Code.IsStore;
            Code.IsStore = true;
            $1.gen();
            Code.IsStore = isStore;
            $3.gen();
            Code.Gen.storeValue($1.Decl);
        }
    }
    
    public final class IfStmt extends Stmt {
        public TerminalNode $1;
        public TerminalNode $2;
        public LogicExprTop $3;
        public TerminalNode $4;
        public Block $5;
        public ElseStmtOpt $6;
        
        protected IfStmt(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            case 4: 
                return $5;
            
            case 5: 
                return $6;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (LogicExprTop)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            case 4: 
                $5 = (Block)node;
                break;
            
            case 5: 
                $6 = (ElseStmtOpt)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $3.prep0();
            $5.prep0();
            $6.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen() {
            LogicExprState state = new LogicExprState(Code.createLabel(), Code.createLabel());
            $3.gen(state);
            Code.Gen.label(state.TrueLabel);
            $5.gen();
            if (!$6.isLambda()) {
                Label behindElseLabel = Code.createLabel();
                Code.Gen.jump(behindElseLabel);
                Code.Gen.label(state.FalseLabel);
                $6.gen();
                Code.Gen.label(behindElseLabel);
            } else {
                Code.Gen.label(state.FalseLabel);
            }
        }
    }
    
    public final class WhileStmt extends Stmt {
        public TerminalNode $1;
        public TerminalNode $2;
        public LogicExprTop $3;
        public TerminalNode $4;
        public Block $5;
        
        protected WhileStmt(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            case 4: 
                return $5;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (LogicExprTop)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            case 4: 
                $5 = (Block)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $3.prep0();
            $5.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen() {
            StartLabel = Code.createLabel();
            Code.pushLoop(this);
            LogicExprState state = new LogicExprState(Code.createLabel(), EndLabel = Code.createLabel());
            Code.Gen.label(StartLabel);
            $3.gen(state);
            Code.Gen.label(state.TrueLabel);
            $5.gen();
            Code.Gen.jump(StartLabel);
            Code.Gen.label(state.FalseLabel);
            Code.popLoop(this);
        }
        Label StartLabel;
        Label EndLabel;
    }
    
    public final class Stmt_3 extends Stmt {
        public TerminalNode $1;
        public TerminalNode $2;
        
        protected Stmt_3(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(3));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            Code.Gen.jump(Code.getCurrentLoop().EndLabel);
        }
    }
    
    public final class Stmt_4 extends Stmt {
        public TerminalNode $1;
        public TerminalNode $2;
        
        protected Stmt_4(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(4));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            Code.Gen.jump(Code.getCurrentLoop().StartLabel);
        }
    }
    
    public final class ScanStmt extends Stmt {
        public TerminalNode $1;
        public TerminalNode $2;
        public ScanVarList $3;
        public TerminalNode $4;
        public TerminalNode $5;
        
        protected ScanStmt(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(5));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            case 4: 
                return $5;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (ScanVarList)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            case 4: 
                $5 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $3.prep0();
        }
        
        public void prep0() {
            Code.HasScanStmt = true;
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $3.gen();
        }
        
        public void gen() {
            boolean isStore = Code.IsStore;
            Code.IsStore = true;
            propagate_gen();
            Code.IsStore = isStore;
        }
    }
    
    public final class Stmt_6 extends Stmt {
        public PrintStatementBase $1;
        
        protected Stmt_6(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(6));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (PrintStatementBase)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class Stmt_7 extends Stmt {
        public Block $1;
        
        protected Stmt_7(int index, Node parent) {
            super(index, parent, StmtSymbol.getRule(7));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (Block)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ElseStmtOpt extends Node {
        
        protected ElseStmtOpt(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ElseStmtOpt_0 extends ElseStmtOpt {
        public TerminalNode $1;
        public Block $2;
        
        protected ElseStmtOpt_0(int index, Node parent) {
            super(index, parent, ElseStmtOptSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (Block)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class ElseStmtOpt_1 extends ElseStmtOpt {
        
        protected ElseStmtOpt_1(int index, Node parent) {
            super(index, parent, ElseStmtOptSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class PrintStatementBase extends Node {
        
        protected PrintStatementBase(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class PrintStmt extends PrintStatementBase {
        public TerminalNode $1;
        public TerminalNode $2;
        public PrintableExprList $3;
        public TerminalNode $4;
        public TerminalNode $5;
        
        protected PrintStmt(int index, Node parent) {
            super(index, parent, PrintStatementBaseSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            case 4: 
                return $5;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (PrintableExprList)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            case 4: 
                $5 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $3.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class PrintlnStmt extends PrintStatementBase {
        public TerminalNode $1;
        public TerminalNode $2;
        public PrintableExprList $3;
        public TerminalNode $4;
        public TerminalNode $5;
        
        protected PrintlnStmt(int index, Node parent) {
            super(index, parent, PrintStatementBaseSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            case 3: 
                return $4;
            
            case 4: 
                return $5;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (TerminalNode)node;
                break;
            
            case 2: 
                $3 = (PrintableExprList)node;
                break;
            
            case 3: 
                $4 = (TerminalNode)node;
                break;
            
            case 4: 
                $5 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $3.gen();
        }
        
        public void gen() {
            propagate_gen();
            Code.Gen.println();
        }
    }
    
    public abstract class PrintableExprList extends Node {
        
        protected PrintableExprList(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class PrintableExprList_0 extends PrintableExprList {
        public PrintableExpr $1;
        public CommaPrintableExprStar $2;
        
        protected PrintableExprList_0(int index, Node parent) {
            super(index, parent, PrintableExprListSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (PrintableExpr)node;
                break;
            
            case 1: 
                $2 = (CommaPrintableExprStar)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class CommaPrintableExprStar extends Node {
        
        protected CommaPrintableExprStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class CommaPrintableExprStar_0 extends CommaPrintableExprStar {
        public TerminalNode $1;
        public PrintableExprList $2;
        
        protected CommaPrintableExprStar_0(int index, Node parent) {
            super(index, parent, CommaPrintableExprStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (PrintableExprList)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class CommaPrintableExprStar_1 extends CommaPrintableExprStar {
        
        protected CommaPrintableExprStar_1(int index, Node parent) {
            super(index, parent, CommaPrintableExprStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class PrintableExpr extends Node {
        
        protected PrintableExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class PrintableExpr_0 extends PrintableExpr {
        public PrintableExprImpl $1;
        
        protected PrintableExpr_0(int index, Node parent) {
            super(index, parent, PrintableExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (PrintableExprImpl)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            Code.Gen.print($1);
        }
    }
    
    public abstract class PrintableExprImpl extends Node {
        
        protected PrintableExprImpl(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class PrintableString extends PrintableExprImpl {
        public TerminalNode $1;
        
        protected PrintableString(int index, Node parent) {
            super(index, parent, PrintableExprImplSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            Code.Gen.ldc($1.Token.stringValue());
        }
    }
    
    public final class PrintableInt extends PrintableExprImpl {
        public ArithExpr $1;
        
        protected PrintableInt(int index, Node parent) {
            super(index, parent, PrintableExprImplSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ScanVarList extends Node {
        
        protected ScanVarList(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ScanVarList_0 extends ScanVarList {
        public ScanVar $1;
        public CommaScanVarStar $2;
        
        protected ScanVarList_0(int index, Node parent) {
            super(index, parent, ScanVarListSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (ScanVar)node;
                break;
            
            case 1: 
                $2 = (CommaScanVarStar)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class CommaScanVarStar extends Node {
        
        protected CommaScanVarStar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class CommaScanVarStar_0 extends CommaScanVarStar {
        public TerminalNode $1;
        public ScanVar $2;
        public CommaScanVarStar $3;
        
        protected CommaScanVarStar_0(int index, Node parent) {
            super(index, parent, CommaScanVarStarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ScanVar)node;
                break;
            
            case 2: 
                $3 = (CommaScanVarStar)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class CommaScanVarStar_1 extends CommaScanVarStar {
        
        protected CommaScanVarStar_1(int index, Node parent) {
            super(index, parent, CommaScanVarStarSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ScanVar extends Node {
        
        protected ScanVar(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ScanVar_0 extends ScanVar {
        public VarOccurrence $1;
        
        protected ScanVar_0(int index, Node parent) {
            super(index, parent, ScanVarSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (VarOccurrence)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            $1.gen();
            Code.Gen.scan();
            Code.Gen.storeValue($1.Decl);
        }
    }
    
    public abstract class ArithExpr extends Node {
        
        protected ArithExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ArithExpr_0 extends ArithExpr {
        public ArithExprProduct $1;
        public ArithExprPlusMinus $2;
        
        protected ArithExpr_0(int index, Node parent) {
            super(index, parent, ArithExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (ArithExprProduct)node;
                break;
            
            case 1: 
                $2 = (ArithExprPlusMinus)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ArithExprProduct extends Node {
        
        protected ArithExprProduct(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ArithExprProduct_0 extends ArithExprProduct {
        public UnaryArithExpr $1;
        public ArithExprProductMulDiv $2;
        
        protected ArithExprProduct_0(int index, Node parent) {
            super(index, parent, ArithExprProductSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (UnaryArithExpr)node;
                break;
            
            case 1: 
                $2 = (ArithExprProductMulDiv)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class UnaryArithExpr extends Node {
        
        protected UnaryArithExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class UnaryArithExpr_0 extends UnaryArithExpr {
        public TerminalNode $1;
        public UnaryArithExprNoPrefix $2;
        
        protected UnaryArithExpr_0(int index, Node parent) {
            super(index, parent, UnaryArithExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (UnaryArithExprNoPrefix)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class UnaryArithExpr_1 extends UnaryArithExpr {
        public TerminalNode $1;
        public UnaryArithExprNoPrefix $2;
        
        protected UnaryArithExpr_1(int index, Node parent) {
            super(index, parent, UnaryArithExprSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (UnaryArithExprNoPrefix)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
            Code.Gen.neg();
        }
    }
    
    public final class UnaryArithExpr_2 extends UnaryArithExpr {
        public UnaryArithExprNoPrefix $1;
        
        protected UnaryArithExpr_2(int index, Node parent) {
            super(index, parent, UnaryArithExprSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (UnaryArithExprNoPrefix)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class UnaryArithExprNoPrefix extends Node {
        
        protected UnaryArithExprNoPrefix(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class UnaryArithExprNoPrefix_0 extends UnaryArithExprNoPrefix {
        public VarOccurrence $1;
        
        protected UnaryArithExprNoPrefix_0(int index, Node parent) {
            super(index, parent, UnaryArithExprNoPrefixSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (VarOccurrence)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $1.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public final class UnaryArithExprNoPrefix_1 extends UnaryArithExprNoPrefix {
        public TerminalNode $1;
        
        protected UnaryArithExprNoPrefix_1(int index, Node parent) {
            super(index, parent, UnaryArithExprNoPrefixSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            Code.Gen.ldc($1.Token.intValue());
        }
    }
    
    public final class UnaryArithExprNoPrefix_2 extends UnaryArithExprNoPrefix {
        public TerminalNode $1;
        public ArithExpr $2;
        public TerminalNode $3;
        
        protected UnaryArithExprNoPrefix_2(int index, Node parent) {
            super(index, parent, UnaryArithExprNoPrefixSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            case 2: 
                $3 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ArithExprPlusMinus extends Node {
        
        protected ArithExprPlusMinus(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ArithExprPlusMinus_0 extends ArithExprPlusMinus {
        public TerminalNode $1;
        public ArithExprProduct $2;
        public ArithExprPlusMinus $3;
        
        protected ArithExprPlusMinus_0(int index, Node parent) {
            super(index, parent, ArithExprPlusMinusSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExprProduct)node;
                break;
            
            case 2: 
                $3 = (ArithExprPlusMinus)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            $2.gen();
            Code.Gen.add();
            $3.gen();
        }
    }
    
    public final class ArithExprPlusMinus_1 extends ArithExprPlusMinus {
        public TerminalNode $1;
        public ArithExprProduct $2;
        public ArithExprPlusMinus $3;
        
        protected ArithExprPlusMinus_1(int index, Node parent) {
            super(index, parent, ArithExprPlusMinusSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExprProduct)node;
                break;
            
            case 2: 
                $3 = (ArithExprPlusMinus)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            $2.gen();
            Code.Gen.sub();
            $3.gen();
        }
    }
    
    public final class ArithExprPlusMinus_2 extends ArithExprPlusMinus {
        
        protected ArithExprPlusMinus_2(int index, Node parent) {
            super(index, parent, ArithExprPlusMinusSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class ArithExprProductMulDiv extends Node {
        
        protected ArithExprProductMulDiv(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen();
    }
    
    public final class ArithExprProductMulDiv_0 extends ArithExprProductMulDiv {
        public TerminalNode $1;
        public UnaryArithExpr $2;
        public ArithExprProductMulDiv $3;
        
        protected ArithExprProductMulDiv_0(int index, Node parent) {
            super(index, parent, ArithExprProductMulDivSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (UnaryArithExpr)node;
                break;
            
            case 2: 
                $3 = (ArithExprProductMulDiv)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            $2.gen();
            Code.Gen.mul();
            $3.gen();
        }
    }
    
    public final class ArithExprProductMulDiv_1 extends ArithExprProductMulDiv {
        public TerminalNode $1;
        public UnaryArithExpr $2;
        public ArithExprProductMulDiv $3;
        
        protected ArithExprProductMulDiv_1(int index, Node parent) {
            super(index, parent, ArithExprProductMulDivSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (UnaryArithExpr)node;
                break;
            
            case 2: 
                $3 = (ArithExprProductMulDiv)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
            $3.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
            $3.gen();
        }
        
        public void gen() {
            $2.gen();
            Code.Gen.div();
            $3.gen();
        }
    }
    
    public final class ArithExprProductMulDiv_2 extends ArithExprProductMulDiv {
        
        protected ArithExprProductMulDiv_2(int index, Node parent) {
            super(index, parent, ArithExprProductMulDivSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen() {
            propagate_gen();
        }
    }
    
    public abstract class LogicExprTop extends Node {
        
        protected LogicExprTop(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState les);
    }
    
    public final class LogicExprTop_0 extends LogicExprTop {
        public LogicExpr $1;
        
        protected LogicExprTop_0(int index, Node parent) {
            super(index, parent, LogicExprTopSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (LogicExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState les) {
            $1.gen(les);
        }
    }
    
    public abstract class LogicExpr extends Node {
        
        protected LogicExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class LogicExpr_0 extends LogicExpr {
        public AndLogicExpr $1;
        public LogicExprOrRHS $2;
        
        protected LogicExpr_0(int index, Node parent) {
            super(index, parent, LogicExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (AndLogicExpr)node;
                break;
            
            case 1: 
                $2 = (LogicExprOrRHS)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            LogicExprState $1State = leState;
            Label rhsLabel = null;
            if (!$2.isLambda()) {
                rhsLabel = Code.createLabel();
                $1State = leState.copy();
                $1State.Left = true;
                $1State.Comparer = CMMTokenName.LogicalOr;
                if (leState.Negated) {
                    $1State.TrueLabel = rhsLabel;
                } else {
                    $1State.FalseLabel = rhsLabel;
                }
            }
            $1.gen($1State);
            if (!$2.isLambda()) {
                Code.Gen.label(rhsLabel);
                $2.gen(leState);
            }
        }
    }
    
    public abstract class LogicExprOrRHS extends Node {
        
        protected LogicExprOrRHS(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class LogicExprOrRHS_0 extends LogicExprOrRHS {
        public TerminalNode $1;
        public LogicExpr $2;
        
        protected LogicExprOrRHS_0(int index, Node parent) {
            super(index, parent, LogicExprOrRHSSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (LogicExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen(leState);
        }
    }
    
    public final class LogicExprOrRHS_1 extends LogicExprOrRHS {
        
        protected LogicExprOrRHS_1(int index, Node parent) {
            super(index, parent, LogicExprOrRHSSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen(LogicExprState leState) {
            propagate_gen();
        }
    }
    
    public abstract class AndLogicExpr extends Node {
        
        protected AndLogicExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class AndLogicExpr_0 extends AndLogicExpr {
        public UnaryLogicExpr $1;
        public LogicExprAndRHS $2;
        
        protected AndLogicExpr_0(int index, Node parent) {
            super(index, parent, AndLogicExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (UnaryLogicExpr)node;
                break;
            
            case 1: 
                $2 = (LogicExprAndRHS)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            LogicExprState $1State = leState;
            Label rhsLabel = null;
            if (!$2.isLambda()) {
                rhsLabel = Code.createLabel();
                $1State = leState.copy();
                $1State.Left = true;
                $1State.Comparer = CMMTokenName.LogicalAnd;
                if (leState.Negated) {
                    $1State.FalseLabel = rhsLabel;
                } else {
                    $1State.TrueLabel = rhsLabel;
                }
            }
            $1.gen($1State);
            if (!$2.isLambda()) {
                Code.Gen.label(rhsLabel);
                $2.gen(leState);
            }
        }
    }
    
    public abstract class LogicExprAndRHS extends Node {
        
        protected LogicExprAndRHS(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class LogicExprAndRHS_0 extends LogicExprAndRHS {
        public TerminalNode $1;
        public AndLogicExpr $2;
        
        protected LogicExprAndRHS_0(int index, Node parent) {
            super(index, parent, LogicExprAndRHSSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (AndLogicExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen(leState);
        }
    }
    
    public final class LogicExprAndRHS_1 extends LogicExprAndRHS {
        
        protected LogicExprAndRHS_1(int index, Node parent) {
            super(index, parent, LogicExprAndRHSSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            }
        }
        
        public void propagate_prep0() {
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
        }
        
        public void gen(LogicExprState leState) {
            propagate_gen();
        }
    }
    
    public abstract class UnaryLogicExpr extends Node {
        
        protected UnaryLogicExpr(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class UnaryLogicExpr_0 extends UnaryLogicExpr {
        public ArithExpr $1;
        public ArithExprComparisonSuffix $2;
        
        protected UnaryLogicExpr_0(int index, Node parent) {
            super(index, parent, UnaryLogicExprSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (ArithExpr)node;
                break;
            
            case 1: 
                $2 = (ArithExprComparisonSuffix)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $1.prep0();
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            $1.gen();
            $2.gen(leState);
        }
    }
    
    public final class UnaryLogicExpr_1 extends UnaryLogicExpr {
        public TerminalNode $1;
        public LogicExpr $2;
        public TerminalNode $3;
        
        protected UnaryLogicExpr_1(int index, Node parent) {
            super(index, parent, UnaryLogicExprSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            case 2: 
                return $3;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (LogicExpr)node;
                break;
            
            case 2: 
                $3 = (TerminalNode)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen(leState);
        }
    }
    
    public final class UnaryLogicExpr_2 extends UnaryLogicExpr {
        public TerminalNode $1;
        public UnaryLogicExpr $2;
        
        protected UnaryLogicExpr_2(int index, Node parent) {
            super(index, parent, UnaryLogicExprSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (UnaryLogicExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void gen(LogicExprState leState) {
            leState.Negated = !leState.Negated;
            $2.gen(leState);
            leState.Negated = !leState.Negated;
        }
    }
    
    public abstract class ArithExprComparisonSuffix extends Node {
        
        protected ArithExprComparisonSuffix(int index, Node parent, Rule rule) {
            super(index, parent, rule);
        }
        
        public abstract void prep0();
        
        public abstract void gen(LogicExprState leState);
    }
    
    public final class ArithExprComparisonSuffix_0 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_0(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(0));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpGT(leState);
        }
    }
    
    public final class ArithExprComparisonSuffix_1 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_1(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(1));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpGE(leState);
        }
    }
    
    public final class ArithExprComparisonSuffix_2 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_2(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(2));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpLT(leState);
        }
    }
    
    public final class ArithExprComparisonSuffix_3 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_3(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(3));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpLE(leState);
        }
    }
    
    public final class ArithExprComparisonSuffix_4 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_4(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(4));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpEQ(leState);
        }
    }
    
    public final class ArithExprComparisonSuffix_5 extends ArithExprComparisonSuffix {
        public TerminalNode $1;
        public ArithExpr $2;
        
        protected ArithExprComparisonSuffix_5(int index, Node parent) {
            super(index, parent, ArithExprComparisonSuffixSymbol.getRule(5));
        }
        
        public Node getChild(int index) {
            switch (index) {
            case 0: 
                return $1;
            
            case 1: 
                return $2;
            
            }
            return null;
        }
        
        public void setChild(Node node) {
            switch (node.index()) {
            case 0: 
                $1 = (TerminalNode)node;
                break;
            
            case 1: 
                $2 = (ArithExpr)node;
                break;
            
            }
        }
        
        public void propagate_prep0() {
            $2.prep0();
        }
        
        public void prep0() {
            propagate_prep0();
        }
        
        public void propagate_gen() {
            $2.gen();
        }
        
        public void gen(LogicExprState leState) {
            $2.gen();
            Code.Gen.cmpNE(leState);
        }
    }
    public final CompilerContext Context;
    public final Scanner Scanner;
    public final Checker Checker;
    public final Code Code;
    
    public CMMGrammar(CompilerContext context, Scanner scanner) {
        _initSymbols();
        Context = context;
        Scanner = scanner;
        Checker = new Checker(Context);
        Code = new Code(Context);
    }
    
    public class VarList extends ArrayList<VarOccurrence> {
    }
    
    public class PrintableExpressionList extends ArrayList<PrintableExpr> {
    }
    
    public class IntegerList extends ArrayList<Integer> {
    }
    
    public class ArithExprList extends ArrayList<ArithExpr> {
    }
    
    public LL1PredictTable createPredictTable() {
        return new LL1PredictTable(this);
    }
    
    public enum BaseType {
        /*public static final*/ Integer /* = new BaseType() */;
    }
}