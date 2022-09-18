option = /IC:\DOCUME~1\Yann\Work\PTIDEJ~3\PTIDEJ~3\Claire\include -c /DCLPC /DCLWIN
.SUFFIXES : .exe .obj .cpp

Z = C:\DOCUME~1\Yann\Work\PTIDEJ~3\PTIDEJ~3\Claire\bin\public\ntv
T = ..\Ptidej\Obj
CC = cl
FILES = $T\ptidej.obj $T\Release.obj  $T\DomainModel.obj  $T\SolverModel.obj  $T\SolverCore.obj  $T\GeneralRepairAlgorithm.obj  $T\AC4Constraints.obj  $T\CustomConstraints.obj  $T\SimpleAutomaticSolver.obj  $T\ConstraintHelpers.obj  $T\SimpleInteractiveSolver.obj  $T\AutomaticSolver.obj  $T\InteractiveSolver.obj  $T\CombinatorialAutomaticSolver.obj  $T\SimpleTests.obj  $T\AssociationDistanceTest.obj  $T\CompositionTest.obj  $T\CreationTest.obj  $T\IgnoranceTest.obj  $T\InheritancePathTest.obj  $T\InheritanceTest.obj  $T\InheritanceTreeDepthTest.obj  $T\useDistanceTest.obj  $T\UseTest.obj  $T\StrictInheritanceTest.obj  $T\AbstractFactoryPattern.obj  $T\ChainOfResponsibilityPattern.obj  $T\CompositePattern.obj  $T\ExtendedCompositePattern.obj  $T\FacadePattern.obj  $T\FactoryMethodPattern.obj  $T\IteratorPattern.obj  $T\MediatorPattern.obj  $T\MementoPattern.obj  $T\ObserverPattern.obj  $T\ProxyPattern.obj  $T\VisitorPattern.obj  $T\BadCompositionTest.obj  $T\GoodInheritance.obj  $T\RedundantTransitivity.obj 
{$T}.cpp{$T}.obj:
	$(CC) $(option) /Fo$T\$(<B).obj /Tp $<
all: ptidej.exe
$Z\ptidej.lib: $(FILES)
	lib /NOLOGO /OUT:$@ $(FILES)
JUNK = /NOLOGO /DEBUG /MAP /STACK:1600000 user32.lib gdi32.lib shell32.lib comdlg32.lib
ptidej.exe: $Z\ptidej.lib $T\ptidej-s.obj
	link /subsystem:console $(JUNK) $Z\Core.lib $Z\Language.lib $Z\Reader.lib $Z\choco.lib $Z\ice.lib $Z\palm.lib $Z\ptidej.lib $Z\Kernel.lib $Z\Console.lib /OUT:ptidej.exe $T\ptidej-s.obj

