option = /IC:\Docume~1\Yann\Work\Ptidej~2\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\Docume~1\Yann\Work\Ptidej~2\Claire\bin\public\ntv
T = C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\obj
CC = cl
FILES = $T\ice.obj $T\matching.obj 
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: $Z\ice.lib
$Z\ice.lib: $(FILES)
	lib /NOLOGO /OUT:$@ $(FILES)
