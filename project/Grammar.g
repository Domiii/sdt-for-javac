Grammar : 
      "grammar" Name "<" JavaType "," JavaType ">" "{" GrammarBody "}" 
    ;

Name : 
      JavaId
    ;

GrammarBody : 
      PassDeclarations? MemberDeclarations? BaseNode? NonTerminal+
    ;

PassDeclarations : 
      "grammarpasses" "{" PassDeclaration* "}"
    ;

BaseNode :
      "grammarnode" Name ImplementingInterfaces MemberDeclarationBlock
    ;

NonTerminal :
      Name MemberDeclarations? ":" Rules? ";"
    ;

PassDeclaration :
      JavaType Name ";"
    ;

MemberDeclarations :
      "grammarmembers" ImplementingInterfaces MemberDeclarationBlock
    ;

ImplementingInterfaces  : 
      "implements" JavaInterfaceType*
    |
    ;

MemberDeclarationBlock: 
      "{" JavaClassMemberDeclaration* "}"
    ;

Rules :
      Rule SeparatedRule*
    ;

SeparatedRule: 
      "|" Rule
    ;

Rule :
      RuleName? Symbol* MemberDeclarations? Pass*
    ;

RuleName :
      "[" Name "]"
    ;

Symbol :
       JavaId
    ;

Pass :
      "("Name JavaParameterList")" JavaMethodBlock
    ;


Javac helps us with the following symbols:
    JavaId
        Java-style identifier.
        
    JavaStringLiteral
        Java-style string literal - e.g. "this is a \"string literal\"".
        
    JavaType
        Any run-time type - e.g. int, PrintStream etc.

    JavaInterfaceType
        Any run-time interface type; i.e. any type defined with the "interface" keyword.

    JavaClassMemberDeclaration
        Anything that is allowed within a class declaration block; can be a field (attribute), method, nested type etc.

    JavaParameterList
        A comma-separated list of parameters to a method. Every parameter has a type and a name.

    JavaMethodBlock
        A block of code, enclosed by "{" and "}" braces.

