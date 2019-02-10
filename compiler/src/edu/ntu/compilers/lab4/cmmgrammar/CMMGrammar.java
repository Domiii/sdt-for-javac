package edu.ntu.compilers.lab4.cmmgrammar;

import edu.ntu.compilers.lab4.parser.*;
import edu.ntu.compilers.lab4.cmmcompiler.*;
import edu.ntu.compilers.lab4.scanner.*;
import edu.ntu.compilers.lab4.tokens.*;

import java.util.ArrayList;

public grammar CMMGrammar<Token, CMMTokenName> {
    // list of passes with return type
    // optional
    grammarpasses {
        /** 
         * The first pass to setup the symbol tables, beautify the AST and generate labels
         * Label generation should generally not be mixed with non-code-generation passes, but CMM is just so simple...
         */
        void prep0;
        
        /**
         * Code generation pass
         */
        void gen;
    }
    
    // custom members of the emitted CMMGrammar class
    // optional
    grammarmembers {
        public final CompilerContext Context;
        public final Scanner Scanner;
        public final Checker Checker;
        public final Code Code;
        
        public CMMGrammar(CompilerContext context, Scanner scanner) {
            Context = context;
            Scanner = scanner;
            Checker = new Checker(Context);
            Code = new Code(Context);
        }

        public class VarList extends ArrayList<VarOccurrence> {}
        public class PrintableExpressionList extends ArrayList<PrintableExpr> {}
        public class IntegerList extends ArrayList<Integer> {}
        public class ArithExprList extends ArrayList<ArithExpr> {}
        
        public LL1PredictTable createPredictTable() {
            return new LL1PredictTable(this);
        }
        
        public enum BaseType {
            Integer
        }
    }
    
    
    // members of the emitted Node class
    // optional
    grammarnode {
        /**
         * The first Stmt that is found to the right of this node
         */
        public Stmt getNextStmt() {
            if (Parent == null) {
                return null;
            }
            return Parent.getFirstStmtToTheRightOf(index());
        }
        
        /**
         * The first Stmt to the right of the child node at the given index.
         */
        public Stmt getFirstStmtToTheRightOf(int index) {
            index++;                            // index of the next child
            if (this instanceof TerminalNode || Rule.getSymbolCount() <= index) {
                // there is no more child to the right, go to parent
                if (Parent == null) {
                    return null;
                }
                return Parent.getFirstStmtToTheRightOf(index());
            }
            
            Node child = getChild(index);
            if (child instanceof Stmt) {
                return (Stmt)child;
            }
            
            // child is not a Stmt -> Keep looking, starting at the first child of the child
            return child.getFirstStmtToTheRightOf(-1);
        }
    }
    
    // all Non-Terminal definitions
    Program : 
          Block 
                                                            (gen) {
                                                                // at this point, we are already inside the main method
                                                                
                                                                if (Code.HasScanStmt) {
                                                                    // allocate and initialize the Scanner object
                                                                    Code.Gen.initScanner();
                                                                }
                                                                
                                                                // generate code
                                                                $1.gen();
                                                                
                                                                // return from method
                                                                Code.Gen.returnn();
                                                            }
        ;
        
    Block 
                                                            grammarmembers {
                                                                public Scope Scope;
                                                            }
        : 
          OpenCurly MultiVarDeclStar StmtStar CloseCurly
                                                            
                                                            (prep0) {
                                                                // create & traverse scope
                                                                Scope = Context.newScope(this);
                                                                
                                                                propagate_prep0();
                                                                
                                                                Context.leaveScope();
                                                            }
                                                            
                                                            (gen) {
                                                                // traverse scope
                                                                Context.enterScope(Scope);
                                                                
                                                                propagate_gen();
                                                                
                                                                Context.leaveScope();
                                                            }
        ;
    
    MultiVarDeclStar : 
          MultiVarDecl MultiVarDeclStar 
        |
        ;

    StmtStar : 
          Stmt StmtStar 
        |
        ;
        
    MultiVarDecl :
          Type VarDeclIdList Semicolon                   
                                                            (prep0) {
                                                                // explicit propagation
                                                                $1.prep0();
                                                                $2.prep0($1);
                                                            }
        ;

    VarDeclIdList : 
          VarDecl CommaIdStar                  
                                                            (prep0 Type type) {
                                                                $1.prep0(type);
                                                                $2.prep0(type);
                                                            }
        ;
        
    VarDecl
                                                            grammarmembers {
                                                                public Scope Scope;
                                                                public Type Type;
                                                                public String Name;
                                                                public int Id;                          // this is the Id'th variable inside the current scope
                                                                
                                                                public String toString() { return Name; }
                                                            }
        : 
          Identifier
                                                            
                                                            (prep0 Type type)  {
                                                                Scope = Context.getCurrentScope();
                                                                Type = type;
                                                                Name = $1.Token.stringValue();
                                                                Id = Scope.declareVar(this);
                                                            }
                                                            
                                                            (gen) {
                                                                // allocate arrays
                                                                if (Type.isArray()) {
                                                                    Code.Gen.newArray(this);
                                                                }
                                                            }
        ;
    
    CommaIdStar : 
          Comma VarDeclIdList
                                                            (prep0 Type type) {
                                                                $2.prep0(type);
                                                            }
        | 
        ;

    Type 
                                                            grammarmembers {
                                                                public BaseType BaseType;
                                                                public final IntegerList ArrayDimensions = new IntegerList();
                                                                
                                                                public boolean isArray() {
                                                                    return ArrayDimensions.size() != 0;
                                                                }
                                                            }
        : 
          Int NumArrayStar
                                                            
                                                            (prep0) {
                                                                BaseType = BaseType.Integer;
                                                                $2.prep0(ArrayDimensions);
                                                            }
        ;
            
    NumArrayStar :
          OpenBrackets NumberLiteral CloseBrackets 
            NumArrayStar             
                                                            (prep0 IntegerList dims) {
                                                                dims.add($2.Token.intValue());
                                                                
                                                                $4.prep0(dims);
                                                            }
        | 
        ;

    VarOccurrence 
                                                            grammarmembers {
                                                                public String Name;
                                                                public final ArithExprList ArrayDimensions = new ArithExprList();
                                                                public VarDecl Decl;
                                                                
                                                                public boolean isArray() {
                                                                    return ArrayDimensions.size() != 0;
                                                                }
                                                            }
        :
          Identifier ArithArrayStar              
                                                            (prep0) {
                                                                Name = $1.Token.stringValue();
                                                                $2.prep0(ArrayDimensions);
                                                                
                                                                // make sure that the given identifier exists and that it has the correct amount of array dimensions
                                                                // and if so, associate it with it's declaration
                                                                Context.getCurrentScope().lookupVariable(this);
                                                                assert(Decl != null);
                                                            }
                                                            
                                                            (gen) {
                                                                assert(Name != null && Decl != null);
                                                                
                                                                if (isArray()) {
                                                                    Code.Gen.aload(this);                   // load reference
                                                                    
                                                                    // during array generation, we always must load, not store
                                                                    
                                                                    boolean isStore = Code.IsStore;         // save old value
                                                                    Code.IsStore = false;
                                                                    $2.gen();                               // generate array accessors
                                                                    
                                                                    Code.IsStore = isStore;                 // restore old value
                                                                    
                                                                    if (!Code.IsStore) {
                                                                        // load value from array
                                                                        Code.Gen.iaload(this);              // load value
                                                                    }
                                                                }
                                                                else if (!Code.IsStore) {
                                                                    Code.Gen.iload(this);                   // load value
                                                                }
                                                                else {
                                                                    // we do not generate anything for the LHS of a non-array variable occurence
                                                                }
                                                            }
        ;

    ArithArrayStar :
          OpenBrackets ArithExpr CloseBrackets 
            ArithArrayStar   
                                                            (prep0 ArithExprList dims) {
                                                                $2.prep0();
                                                                
                                                                dims.add($2);
                                                                $4.prep0(dims);
                                                            }
                                                            
                                                            (gen) {
                                                                $2.gen();                       // calculate expression
                                                                
                                                                if (!$4.isLambda()) {
                                                                    // load array reference
                                                                    Code.Gen.aaload();
                                                                    
                                                                    // generate next array accessor
                                                                    $4.gen();
                                                                }
                                                                
                                                            }
        | 
        ;

        
    Stmt
        :
          [Assignment] 
          VarOccurrence Equal ArithExpr Semicolon               
                                                            (gen) {
                                                                boolean isStore = Code.IsStore;     // save old value
                                                                Code.IsStore = true;
                                                                
                                                                $1.gen();                           // load address to store (if necessary)
                                                                
                                                                Code.IsStore = isStore;             // restore old value
                                                                
                                                                $3.gen();                           // calculate result
                                                                
                                                                Code.Gen.storeValue($1.Decl);       // store result
                                                            }

        | [IfStmt]
          If OpenParentheses LogicExprTop 
            CloseParentheses Block ElseStmtOpt              
                                                            (gen) {
                                                                LogicExprState state = new LogicExprState(
                                                                    Code.createLabel(),
                                                                    Code.createLabel());
                                                                
                                                                // gen LogicExpr
                                                                $3.gen(state);
                                                                
                                                                // gen True label
                                                                Code.Gen.label(state.TrueLabel);
                                                                
                                                                // gen If Block
                                                                $5.gen();
                                                                
                                                                if (!$6.isLambda()) {
                                                                    // jump over else block, if we took the "True path"
                                                                    Label behindElseLabel = Code.createLabel();
                                                                    Code.Gen.jump(behindElseLabel);
                                                                    
                                                                    // gen labeled Else Block
                                                                    Code.Gen.label(state.FalseLabel);
                                                                    $6.gen();
                                                                    
                                                                    // gen label behind statement
                                                                    Code.Gen.label(behindElseLabel);
                                                                }
                                                                else {
                                                                    // gen FalseLabel
                                                                    Code.Gen.label(state.FalseLabel);
                                                                }
                                                            }
                                                        
        | [WhileStmt]
          While OpenParentheses LogicExprTop 
            CloseParentheses Block                     
                                                            grammarmembers {
                                                                Label StartLabel, EndLabel;
                                                            }
                                                            
                                                            (gen) {
                                                                StartLabel = Code.createLabel();
                                                                
                                                                Code.pushLoop(this);                            // remember loop for break and continue statements
                                                                
                                                                LogicExprState state = new LogicExprState(
                                                                    Code.createLabel(),
                                                                    EndLabel = Code.createLabel());
                                                                
                                                                // mark beginning of loop
                                                                Code.Gen.label(StartLabel);
                                                                
                                                                // gen LogicExpr
                                                                $3.gen(state);
                                                                
                                                                // gen TrueLabel
                                                                Code.Gen.label(state.TrueLabel);
                                                                
                                                                // gen loop block
                                                                $5.gen();
                                                                
                                                                // jump back to beginning of loop
                                                                Code.Gen.jump(StartLabel);
                                                                
                                                                // gen FalseLabel
                                                                Code.Gen.label(state.FalseLabel);
                                                                
                                                                Code.popLoop(this);
                                                            }

        | Break Semicolon                                   
                                                            (gen) {
                                                                // GOTO first instruction behind the current loop
                                                                Code.Gen.jump(Code.getCurrentLoop().EndLabel);
                                                            }
                                                        
        | Continue Semicolon                                  
                                                            (gen) {
                                                                // GOTO beginning of current loop
                                                                Code.Gen.jump(Code.getCurrentLoop().StartLabel);
                                                            }

        | [ScanStmt]
          Scan OpenParentheses ScanVarList 
          CloseParentheses Semicolon            
                                                            (prep0) {   
                                                                // at least one scan statement -> need to generate the code to initialize the scanner
                                                                Code.HasScanStmt = true;
                                                                propagate_prep0();
                                                            }
                                                            
                                                            (gen) {
                                                                boolean isStore = Code.IsStore;     // save old value
                                                                Code.IsStore = true;
                                                                
                                                                propagate_gen();
                                                                
                                                                Code.IsStore = isStore;             // restore old value of IsStore
                                                            }

        |
          PrintStatementBase
          
        | 
          Block
        ;
         
    ElseStmtOpt : 
        Else Block
        | 
        ;
        
    PrintStatementBase
        :
          [PrintStmt]
          Print OpenParentheses PrintableExprList
            CloseParentheses Semicolon
        | 
          [PrintlnStmt]
          Println OpenParentheses PrintableExprList
            CloseParentheses Semicolon
                                                            (gen) {
                                                                propagate_gen();
                                                                
                                                                Code.Gen.println();     // print newline at the end
                                                            }
        ;
        
    PrintableExprList :
            PrintableExpr CommaPrintableExprStar
        ;
        
    CommaPrintableExprStar : 
          Comma PrintableExprList
        |
        ;
    
    // create a single NonTerminal that applies each path to all rules of the nested NonTerminal
    // can be substituted by a method that allows NonTerminals to implement passes for all of it's rules
    PrintableExpr 
        :
          PrintableExprImpl
                                                            (gen) {
                                                                Code.Gen.print($1);     // print
                                                            }
        ;
        
    PrintableExprImpl : 
          [PrintableString]
          StringLiteral
                                                            (gen) {
                                                                // load constant
                                                                Code.Gen.ldc($1.Token.stringValue());
                                                            }
        | [PrintableInt]
          ArithExpr                  
        ;
    
    ScanVarList :
          ScanVar CommaScanVarStar
        ;

    CommaScanVarStar :
          Comma ScanVar CommaScanVarStar       
        | 
        ;
        
    ScanVar :
          VarOccurrence
                                                            (gen) {
                                                                $1.gen();                           // load address, if necessary
                                                                Code.Gen.scan();                    // call scanner method
                                                                Code.Gen.storeValue($1.Decl);       // store return value
                                                            }
        ;
    
    
    
    // arithmetic expressions
    
    ArithExpr : 
          ArithExprProduct ArithExprPlusMinus
        ;
        
    ArithExprProduct : 
          UnaryArithExpr ArithExprProductMulDiv
        ;

    UnaryArithExpr :
    
          Plus UnaryArithExprNoPrefix                            // does nothing
          
        | Minus UnaryArithExprNoPrefix                        
                                                            (gen) {
                                                                // negates the number
                                                                propagate_gen();
                                                                Code.Gen.neg();
                                                            }
        | UnaryArithExprNoPrefix
        ;
        
    UnaryArithExprNoPrefix :
          VarOccurrence                     
        | NumberLiteral                                               
                                                            (gen) {
                                                                Code.Gen.ldc($1.Token.intValue());
                                                            }
        | OpenParentheses ArithExpr CloseParentheses
        ;
        
    ArithExprPlusMinus :
          Plus ArithExprProduct ArithExprPlusMinus                    
                                                            (gen) {
                                                                $2.gen();
                                                                Code.Gen.add();
                                                                
                                                                $3.gen();
                                                            }
        | Minus ArithExprProduct ArithExprPlusMinus                   
        
                                                            (gen) {
                                                                $2.gen();
                                                                Code.Gen.sub();
                                                                
                                                                $3.gen();
                                                            } 
        |
        ;
        
    ArithExprProductMulDiv :
          Star UnaryArithExpr ArithExprProductMulDiv
                                                            (gen) {
                                                                $2.gen();
                                                                Code.Gen.mul();
                                                                
                                                                $3.gen();
                                                            }

        | Slash UnaryArithExpr ArithExprProductMulDiv
                                                            (gen) {
                                                                $2.gen();
                                                                Code.Gen.div();
                                                                
                                                                $3.gen();
                                                            }
        |
        ;

        
    // logic expressions
        
    LogicExprTop : 
          LogicExpr
                                                            (gen LogicExprState les) {
                                                                $1.gen(les);
                                                            }
        ;
        
    LogicExpr : 
          AndLogicExpr LogicExprOrRHS
                                                            (gen LogicExprState leState) {
                                                                LogicExprState $1State = leState;
                                                                Label rhsLabel = null;
                                                                if (!$2.isLambda()) {
                                                                    // RHS of "||" exists
                                                                    rhsLabel = Code.createLabel();
                                                                    
                                                                    $1State = leState.copy();
                                                                    $1State.Left = true;
                                                                    $1State.Comparer = CMMTokenName.LogicalOr;
                                                                    
                                                                    if (leState.Negated) {
                                                                        // we jump to the RHS, if the LHS is true
                                                                        $1State.TrueLabel = rhsLabel;
                                                                    }
                                                                    else {
                                                                        // we jump to the RHS, if the LHS is false
                                                                        $1State.FalseLabel = rhsLabel;
                                                                    }
                                                                }
                                                                
                                                                $1.gen($1State);
                                                                
                                                                if (!$2.isLambda()) {
                                                                    // right of or
                                                                    // generate label to jump to
                                                                    Code.Gen.label(rhsLabel);
                                                                    $2.gen(leState);
                                                                }
                                                            }
        ;
    
    LogicExprOrRHS         // represents the RHS of an "||"
        :
          LogicalOr LogicExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen(leState);
                                                            }
        |  
        ;
        
    AndLogicExpr : 
          UnaryLogicExpr LogicExprAndRHS 
                                                            (gen LogicExprState leState) {
                                                                LogicExprState $1State = leState;
                                                                Label rhsLabel = null;
                                                                if (!$2.isLambda()) {
                                                                    // RHS of "&&" exists
                                                                    
                                                                    rhsLabel = Code.createLabel();
                                                                    
                                                                    $1State = leState.copy();
                                                                    $1State.Left = true;
                                                                    $1State.Comparer = CMMTokenName.LogicalAnd;
                                                                    
                                                                    if (leState.Negated) {
                                                                        // we jump to the RHS, if the LHS is false
                                                                        $1State.FalseLabel = rhsLabel;
                                                                    }
                                                                    else {
                                                                        // we jump to the RHS, if the LHS is true
                                                                        $1State.TrueLabel = rhsLabel;
                                                                    }
                                                                }
                                                                
                                                                $1.gen($1State);
                                                                
                                                                if (!$2.isLambda()) {
                                                                    // right of and
                                                                    // generate label to jump to
                                                                    Code.Gen.label(rhsLabel);
                                                                    $2.gen(leState);
                                                                }
                                                            }
        ;
        
    LogicExprAndRHS        // represents the RHS of an "&&"
        :
          LogicalAnd AndLogicExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen(leState);
                                                            }
        |
        ;

    UnaryLogicExpr :
          ArithExpr ArithExprComparisonSuffix
                                                            (gen LogicExprState leState) {
                                                                $1.gen();
                                                                $2.gen(leState);
                                                            }
        | OpenBrackets LogicExpr CloseBrackets
                                                            (gen LogicExprState leState) {
                                                                $2.gen(leState);
                                                            }

        | Exclamation UnaryLogicExpr
                                                            (gen LogicExprState leState) {
                                                                leState.Negated = !leState.Negated;
                                                                $2.gen(leState);
                                                                leState.Negated = !leState.Negated;
                                                            }
        ;
        
    ArithExprComparisonSuffix :
          Greater ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpGT(leState);
                                                            }
                                                            
        | GreaterEqual ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpGE(leState);
                                                            }
                                                            
        | Less  ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpLT(leState);
                                                            }
                                                            
        | LessEqual ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpLE(leState);
                                                            }
                                                            
        | EqualEqual ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpEQ(leState);
                                                            }
                                                            
        | NotEqual ArithExpr
                                                            (gen LogicExprState leState) {
                                                                $2.gen();
                                                                Code.Gen.cmpNE(leState);
                                                            }
        ;
}