BINPATH = d:/progs/Git/bin/
JAVAPATH = d:/progs/java/jsdk1.6.20/bin/

BINPATH = ''
JAVAPATH = ''


# target run 用來測試自創測試檔，驗收的 Makefile 不會用到
# 參數 $(PROG) -- 不含 path，不含副檔名的測試程式名稱 (如：swap)
# 參數 $(DATA) -- 整數，代表測試資料編號 (如：1)
# run 的效果是：
#   (1) 由這一層的 swap.cmm 先產生其 assembly，然後組譯成執行檔
# target judgeRun 一定要有，驗收的 Makefile 會用到
# 參數 $(PROG) -- 不含 path，不含副檔名的測試程式名稱 (如：swap)
# 參數 $(DATA) -- 整數，代表測試資料編號 (如：1)
# judgeRun 的效果是：
#   (1) 由上一層的 swap.cmm 先產生其 assembly，然後組譯成執行檔
#   (2) 餵入上一層的 swap.cmm.1，產生這一層的 swap.cmm.1.out
judgeRun: compileApp runAppNoDependencies

compileApp: compileCompiler compileAppNoDependencies

runAppNoDependencies: compileAppNoDependencies
	$(JAVAPATH)java -cp "." $(PROG) < ../$(PROG).cmm.$(DATA) > $(PROG).cmm.$(DATA).out
    
		
judgequick:
	$(MAKE) runAppNoDependencies PROG=$(PROG) DATA=$(DATA)
	diff -s --strip-trailing-cr $(PROG).cmm.$(DATA).out ../$(PROG).cmm.$(DATA).out

	
# 這個 target 是用來達成上述效果 (1) 的
compileAppNoDependencies:
	$(BINPATH)mkdir -p genAsm	
	$(BINPATH)mkdir -p classes
	
	java -cp compiler/classes edu.ntu.compilers.lab4.cmmcompiler.CMMCompiler $(PROG) ../$(PROG).cmm
	java -jar ../bass/bass.jar genAsm/$(PROG).bass

compileCompiler: compileJavac compileCompilerNoDependencies

# use javac to compile the compiler
compileCompilerNoDependencies: findCompilerFiles
	$(BINPATH)mkdir -p compiler/classes
	
	$(JAVAPATH)java -cp javac/classes/ com.sun.tools.javac.Main -d compiler/classes @compiler/__file.list


findCompilerFiles:
	$(BINPATH)find compiler/src -name \*.java -print > compiler/__file.list

	
compileJavac:
	make compile -C javac

	
# target clean 一定要有，驗收的 Makefile 會用到
# clean 的效果必須清掉 compiler 程式執行檔、測試程式 assembly 檔、
#					  測試程式執行檔、測試程式執行結果
clean:
	make clean -C javac
	rm -f compiler/__file.list
	rm -f -r compiler/classes
	rm -f __file.list
	rm -f -r classes
	rm -f -r genAsm
	rm -f *.out
