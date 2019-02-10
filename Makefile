BINPATH = d:/progs/Git/bin/
JAVAPATH = d:/progs/java/jsdk1.6.20/bin/

BINPATH = ''
JAVAPATH = ''


# target run �ΨӴ��զ۳д����ɡA�禬�� Makefile ���|�Ψ�
# �Ѽ� $(PROG) -- ���t path�A���t���ɦW�����յ{���W�� (�p�Gswap)
# �Ѽ� $(DATA) -- ��ơA�N����ո�ƽs�� (�p�G1)
# run ���ĪG�O�G
#   (1) �ѳo�@�h�� swap.cmm �����ͨ� assembly�A�M���Ķ��������
# target judgeRun �@�w�n���A�禬�� Makefile �|�Ψ�
# �Ѽ� $(PROG) -- ���t path�A���t���ɦW�����յ{���W�� (�p�Gswap)
# �Ѽ� $(DATA) -- ��ơA�N����ո�ƽs�� (�p�G1)
# judgeRun ���ĪG�O�G
#   (1) �ѤW�@�h�� swap.cmm �����ͨ� assembly�A�M���Ķ��������
#   (2) ���J�W�@�h�� swap.cmm.1�A���ͳo�@�h�� swap.cmm.1.out
judgeRun: compileApp runAppNoDependencies

compileApp: compileCompiler compileAppNoDependencies

runAppNoDependencies: compileAppNoDependencies
	$(JAVAPATH)java -cp "." $(PROG) < ../$(PROG).cmm.$(DATA) > $(PROG).cmm.$(DATA).out
    
		
judgequick:
	$(MAKE) runAppNoDependencies PROG=$(PROG) DATA=$(DATA)
	diff -s --strip-trailing-cr $(PROG).cmm.$(DATA).out ../$(PROG).cmm.$(DATA).out

	
# �o�� target �O�ΨӹF���W�z�ĪG (1) ��
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

	
# target clean �@�w�n���A�禬�� Makefile �|�Ψ�
# clean ���ĪG�����M�� compiler �{�������ɡB���յ{�� assembly �ɡB
#					  ���յ{�������ɡB���յ{�����浲�G
clean:
	make clean -C javac
	rm -f compiler/__file.list
	rm -f -r compiler/classes
	rm -f __file.list
	rm -f -r classes
	rm -f -r genAsm
	rm -f *.out
