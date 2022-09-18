option = /IC:\Docume~1\Yann\Work\Ptidej~2\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\Docume~1\Yann\Work\Ptidej~2\Claire\bin\public\ntv
T = obj
CC = cl
FILES = $T\choco.obj $T\chocutils.obj  $T\model.obj  $T\dom.obj  $T\prop.obj  $T\var.obj  $T\const.obj  $T\intconst1.obj  $T\intconst2.obj  $T\boolconst.obj  $T\setconst.obj  $T\search.obj  $T\chocapi.obj  $T\opapi.obj  $T\compil.obj 
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: choco.exe
$Z\choco.lib: $(FILES)
	lib /NOLOGO /OUT:$@ $(FILES)
JUNK = /NOLOGO /DEBUG /MAP /STACK:1600000 user32.lib gdi32.lib shell32.lib comdlg32.lib
choco.exe: $Z\choco.lib $T\choco-s.obj
	link /subsystem:console $(JUNK) $Z\Core.lib $Z\Language.lib $Z\Reader.lib $Z\choco.lib $Z\Kernel.lib $Z\Console.lib /OUT:choco.exe $T\choco-s.obj

