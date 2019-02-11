# sdt-for-javac [from 2011]
Syntax-Directed Translation for Javac: Modified Javac to compile a new language (C-Minus-Minus).

## Main Points
* In this project I modified Javac (`openjdk-6-src-b22-28_feb_2011`) for building a compiler-compiler using [Syntax-Directed Translation (SDT)](https://www.google.com/search?safe=off&rlz=&q=syntax+directed+translation).
* I added a new construct to the Javac parser called `grammar`, representing a full-fledged Syntax-Directed Definition which maps a [CFG](https://en.wikipedia.org/wiki/Context-free_grammar) to its production rules
  * You can find the new `grammar` construct [here in the javac source code](https://github.com/Domiii/sdt-for-javac/tree/master/javac/com/sun/tools/javac/parser/grammar)
* I then used that `grammar` to build a compiler for a simple language called C-Minus-Minus (short: CMM) [[CMM specs pdf](https://github.com/Domiii/sdt-for-javac/blob/master/cmm_def.pdf)] [[CMM samples](https://github.com/Domiii/sdt-for-javac/tree/master/cmm_samples)] [[my CMM compiler source code](https://github.com/Domiii/sdt-for-javac/tree/master/compiler/src/edu/ntu/compilers/lab4)] and convert it to a proprietary assembly language called `BASS` ([BASS documentation + examples](https://github.com/Domiii/sdt-for-javac/tree/master/bass)).
* I even [patched a bug](https://github.com/Domiii/sdt-for-javac/blob/master/project/bugfix/bugfix.txt) in Javac (enum parsing was broken) that was in that build (`openjdk-6-src-b22-28_feb_2011`) [[official download link](http://download.java.net/openjdk/jdk6/promoted/b22/openjdk-6-src-b22-28_feb_2011.tar.gz)]
* Within my CMM compiler, the language is defined in [the CMMGrammar](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java).
    * NOTE: You see this right - This `.java` file does not contain a `class`, `interface` etc... but instead it defines a `public grammar CMMGrammar` - That is why I had to make modifications to Javac! 😊


## Compiler Steps
The [CMMCompiler](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmcompiler/CMMCompiler.java) uses the [CMMGrammar](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java) to compile everything in a few simple steps.
1. The [CMMParser](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/parser/CMMParser.java#L103) builds the AST, based on the Grammar rules
    * The parser internally calls the [Scanner](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/scanner/Scanner.java) which generates a token stream
1. When the AST is ready, we start running through it and execute the grammar-defined rules.
    * The `grammar` can define an arbitrary amount of `grammarpasses`, and [the CMMGrammar](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java) defines two, each with their own production rules (code to be executed when matching the lefthand-side patterns in the AST): `prep0` to prepare everything and `gen` to generate code (using [the Gen class](https://github.com/Domiii/sdt-for-javac/blob/master/compiler/src/edu/ntu/compilers/lab4/cmmcompiler/Gen.java))
1. The CMMGrammar is only 724 lines long (including comments!). Our modification to the Javac parser then takes that and converts it into a regular Java class before letting the Javac code generator worry about the rest.
    * NOTE: You find that [the equivalent `CMMGrammar` class](https://github.com/Domiii/sdt-for-javac/blob/master/project/cmmsrc/edu/ntu/compilers/lab4/cmmgrammar/CMMGrammar.java) emitted by our modified Javac has over 5000 lines, that's almost 7 times as many lines, and does contain any comments!
1. I then used this compiler to successfully compile [these 7 sample CMM programs](https://github.com/Domiii/sdt-for-javac/tree/master/cmm_samples) to [BASS assembly code](https://github.com/Domiii/sdt-for-javac/tree/master/bass)

You can find the initial proposal and 2011 [final report](https://github.com/Domiii/sdt-for-javac/blob/master/project/report.pdf) [in this folder](https://github.com/Domiii/sdt-for-javac/tree/master/project).
NOTE: The report was not very well written (I loved coding those thousands of lines, but report writing I did not enjoy back then...)
