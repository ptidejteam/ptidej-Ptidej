@echo off

rem C:\Progra~1\Develo~1\Microsoft.NET\FrameworkSDK\Bin\;C:\Progra~1\Microsoft Visual Studio.NET\Vc7\bin\;C:\Progra~1\Microsoft Visual Studio.NET\Common7\IDE\;C:\WINNT\Microsoft.NET\Framework\v1.0.2914\;C:\Progra~1\Microsoft Visual Studio.NET\Vc7\bin\;C:\Progra~1\Microsoft Visual Studio.NET\Common7\IDE\;
rem C:\Progra~1\Develo~1\Microsoft Visual Studio\Common\Tools\WinNT;C:\Progra~1\Develo~1\Microsoft Visual Studio\Common\MSDev98\Bin

rem
rem Root of Visual Developer Studio Common files.
set VSCommonDir=C:\PROGRA~1\MIAF9D~1\Common

rem
rem Root of Visual Developer Studio installed files.
rem
set MSDevDir=C:\PROGRA~1\MIAF9D~1\Common\msdev98

rem
rem Root of Visual C++ installed files.
rem
set MSVCDir=c:\PROGRA~1\DEVELO~1\MICROS~1

rem
rem VcOsDir is used to help create either a Windows 95 or Windows NT specific path.
rem
set VcOsDir=WIN95
if "%OS%" == "Windows_NT" set VcOsDir=WINNT

rem
echo Setting environment for using Microsoft Visual C++ tools.
rem

if "%OS%" == "Windows_NT" set PATH=%MSDevDir%\BIN;%MSVCDir%\BIN;%VSCommonDir%\TOOLS\%VcOsDir%;%VSCommonDir%\TOOLS;%PATH%
if "%OS%" == "" set PATH="%MSDevDir%\BIN";"%MSVCDir%\BIN";"%VSCommonDir%\TOOLS\%VcOsDir%";"%VSCommonDir%\TOOLS";"%windir%\SYSTEM";"%PATH%"
set INCLUDE=%MSVCDir%\ATL\INCLUDE;%MSVCDir%\INCLUDE;%MSVCDir%\MFC\INCLUDE;%INCLUDE%
set LIB=%MSVCDir%\LIB;%MSVCDir%\MFC\LIB;%LIB%

set VcOsDir=
set VSCommonDir=

SET PATH=C:\Docume~1\Yann\Work\Ptidej Solver 3\Claire\bin\public\ntv;%PATH%
CD C:\Documents and Settings\Yann\Work\Ptidej Solver 3\Choco
Claire -s 4 4 -cm choco
PAUSE