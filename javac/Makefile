BINPATH = d:/progs/Git/bin/
JAVAPATH = d:/progs/java/jsdk1.6.20/bin/

BINPATH = ''
JAVAPATH = ''

run: compile
	java -cp classes com.sun.tools.javac.Main

# copy all resource files and compile javac
compile:
	$(BINPATH)mkdir -p classes
	$(BINPATH)find . -name '*.java' -print > __file.list
	$(JAVAPATH)javac -d classes @__file.list
	
	cp -rf ./com/sun/tools/apt/resources ./classes/com/sun/tools/apt/resources
	cp -rf ./com/sun/tools/doclets/formats/html/resources ./classes/com/sun/tools/doclets/formats/html/resources
	cp -rf ./com/sun/tools/doclets/internal/toolkit/resources ./classes/com/sun/tools/doclets/internal/toolkit/resources
	cp -rf ./com/sun/tools/javac/resources ./classes/com/sun/tools/javac/resources
	cp -rf ./com/sun/tools/javadoc/resources ./classes/com/sun/tools/javadoc/resources
	cp -rf ./com/sun/tools/javah/resources ./classes/com/sun/tools/javah/resources

clean:
	rm -f __file.list
	rm -f -r classes
	rm -f *.out