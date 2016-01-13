@echo off

rem set jre=jre6-32
rem set jrepath=%cd%\%jre%\lib\rt.jar;%cd%\%jre%\lib\charsets.jar;%cd%\%jre%\lib\jsse.jar

if '%1=='## goto ENVSET

SET LIBDIR=%cd%\..\lib

SET CLSPATH=.
FOR %%c IN (%LIBDIR%\*.jar) DO CALL %0 ## %%c

GOTO RUN

:RUN
echo %jrepath%;%CLSPATH%
java -Xmx128m -classpath "%jrepath%;%CLSPATH%" org.webspider.myspider.SpiderRunner
goto END

:ENVSET
set CLSPATH=%CLSPATH%;%2
goto END

:END
