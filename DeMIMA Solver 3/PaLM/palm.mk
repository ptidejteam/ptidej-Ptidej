option = /IC:\Docume~1\Yann\Work\Ptidej~2\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\Docume~1\Yann\Work\Ptidej~2\Claire\bin\public\ntv
T = C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\obj
CC = cl
FILES = $T\palm.obj $T\p-namespace.obj  $T\p-model.obj  $T\p-domain.obj  $T\p-variables.obj  $T\p-constraints.obj  $T\p-symbolic.obj  $T\p-ac4.obj  $T\p-explain.obj  $T\p-solve.obj  $T\p-bool.obj  $T\palmapi.obj 
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: palm.exe
$Z\palm.lib: $(FILES)
	lib /NOLOGO /OUT:$@ $(FILES)
JUNK = /NOLOGO /DEBUG /MAP /STACK:1600000 user32.lib gdi32.lib shell32.lib comdlg32.lib
palm.exe: $Z\palm.lib $T\palm-s.obj
	link /subsystem:console $(JUNK) $Z\Core.lib $Z\Language.lib $Z\Reader.lib $Z\choco.lib $Z\ice.lib $Z\palm.lib $Z\Kernel.lib $Z\Console.lib /OUT:palm.exe $T\palm-s.obj

