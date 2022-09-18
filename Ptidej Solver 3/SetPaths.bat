@ECHO OFF

SET CLAIRE3_HOME=C:\DOCUME~1\Yann\Work\PTIDEJ~3\PTIDEJ~3\Claire

SET VSCommonDir=C:\PROGRA~1\MIAF9D~1\Common
SET MSDevDir=C:\PROGRA~1\MIAF9D~1\Common\msdev98
SET MSVCDir=c:\PROGRA~1\DEVELO~1\MICROS~1
SET VcOsDir=WIN95
IF "%OS%" == "Windows_NT" set VcOsDir=WINNT
IF "%OS%" == "Windows_NT" set PATH=%MSDevDir%\BIN;%MSVCDir%\BIN;%VSCommonDir%\TOOLS\%VcOsDir%;%VSCommonDir%\TOOLS;%PATH%
IF "%OS%" == "" set PATH="%MSDevDir%\BIN";"%MSVCDir%\BIN";"%VSCommonDir%\TOOLS\%VcOsDir%";"%VSCommonDir%\TOOLS";"%windir%\SYSTEM";"%PATH%"
SET INCLUDE=%MSVCDir%\ATL\INCLUDE;%MSVCDir%\INCLUDE;%MSVCDir%\MFC\INCLUDE;%INCLUDE%
SET LIB=%MSVCDir%\LIB;%MSVCDir%\MFC\LIB;%LIB%

SET PATH=%CLAIRE3_HOME%\bin\public\ntv\;%PATH%

ECHO Done.