option = /IC:\Docume~1\Yann\Work\Ptidej~2\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\Docume~1\Yann\Work\Ptidej~2\Claire\bin\public\ntv
T = C:\Docume~1\Yann\Work\Ptidej~2\Iceberg\obj
CC = cl
FILES = $T\ice.obj $T\wcsp.obj  $T\matching.obj 
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: ice.exe
$Z\ice.lib: $(FILES)
	lib /NOLOGO /OUT:$@ $(FILES)
JUNK = /NOLOGO /DEBUG /MAP /STACK:1600000 user32.lib gdi32.lib shell32.lib comdlg32.lib
ice.exe: $Z\ice.lib $T\ice-s.obj
	link /subsystem:console $(JUNK) $Z\Core.lib $Z\Language.lib $Z\Reader.lib $Z\choco.lib $Z\ice.lib $Z\Kernel.lib $Z\Console.lib /OUT:ice.exe $T\ice-s.obj

