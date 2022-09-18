@echo off
set TMP_CLASSPATH=%CLASSPATH%

for %%i in (%0) do cd /d %%~dpi\..


set CLASSPATH=%CLASSPATH%;.\classes\;.\fonts\;

rem for %%i in ("%FLEX_SDK_HOME%\lib\*.jar") do call ".\bin\cpappend.bat" %%i

rem Add all jars....
for %%i in (".\lib\*.jar") do call ".\bin\cpappend.bat" %%i
for %%i in (".\lib\*.zip") do call ".\bin\cpappend.bat" %%i



set IREPORT_CLASSPATH=%CLASSPATH%
set CLASSPATH=%TMP_CLASSPATH%

if not "%IREPORT_HOME%" == "" goto gotIReportHome
set IREPORT_HOME=%CD%
:gotIReportHome


java -cp "%IREPORT_CLASSPATH%" -Direport.home="%IREPORT_HOME%" -Djava.security.policy="%IREPORT_HOME%/policy.all" -Xms128m -Xmx512m it.businesslogic.ireport.gui.MainFrame %*

