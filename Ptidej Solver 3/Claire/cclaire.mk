option = /IC:\Docume~1\Yann\Work\Ptidej~2\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\Docume~1\Yann\Work\Ptidej~2\Claire\bin\public\ntv
T = C:\Docume~1\Yann\Work\Ptidej~2\Claire\csrc
CC = cl
FILES = $T\cclaire-s.obj $T\ccmain.obj
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: cclaire.exe
JUNK = /NOLOGO /DEBUG /MAP /STACK:1600000 user32.lib gdi32.lib shell32.lib comdlg32.lib
cclaire.exe: $(FILES)
	link /subsystem:console $(JUNK) $Z\Core.lib $Z\Language.lib $Z\Reader.lib $Z\Optimize.lib $Z\Generate.lib $Z\Kernel.lib $Z\Console.lib /OUT:cclaire.exe $(FILES)

