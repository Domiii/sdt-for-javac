# sdt-for-javac [from 2011]
Syntax-Directed Translation for Javac: Modified Javac to compile a new language (C-Minus-Minus).

## Main Points
* I modified Javac v6 to allow defining a Syntax-Directed Definition, a grammar, and a fully working compiler based on that grammar, within the language itself.
  * You can find the new `grammar` construct [here in the javac source code](https://github.com/Domiii/sdt-for-javac/tree/master/javac/com/sun/tools/javac/parser/grammar)
* I then used that grammar to build a compiler for a simple language called C-Minus-Minus [[source](https://github.com/Domiii/sdt-for-javac/tree/master/compiler/src/edu/ntu/compilers/lab4)]
* I even [patched a bug](https://github.com/Domiii/sdt-for-javac/blob/master/project/bugfix/bugfix.txt) in Javac that was in that build (`openjdk-6-src-b22-28_feb_2011`) [[official download link](http://download.java.net/openjdk/jdk6/promoted/b22/openjdk-6-src-b22-28_feb_2011.tar.gz)]
* The CMM language is defined in [this Grammar construct](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java).
    * NOTE: This file does not contain a `class`. You see this right: It defines a `public grammar CMMGrammar` 😊
* The [CMMCompiler](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmcompiler/CMMCompiler.java) then uses that and does all it's things:


## Compiler Steps
1. The [CMMParser](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/parser/CMMParser.java#L103) builds the AST, based on the Grammar rules
    * The parser internally calls the [Scanner](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/scanner/Scanner.java) which generates a token stream
1. When the AST is ready, we start run through it and execute the grammar-defined rules.
    * The grammar can define an arbitrary amount of parses, but we have two: `prep0` to prepare everything and `gen` which uses [Gen](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmcompiler/Gen.java) for code generation
1. The CMMGrammar is only 720 lines long. Our modification to the Javac parser then takes that and converts it into a regular Java class before letting the Javac code generator worry about the rest.
    * You find that [the emitted Java class](https://github.com/Domiii/sdt-for-javac/blob/master/project/cmmsrc/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java) has over 5000 lines.

You can find the initial proposal and 2011 [final report](https://github.com/Domiii/sdt-for-javac/blob/master/project/report.pdf) [in this folder](https://github.com/Domiii/sdt-for-javac/tree/master/project).
NOTE: The report was not very well written (I loved coding those thousands of lines, but report writing I did not enjoy back then...)
