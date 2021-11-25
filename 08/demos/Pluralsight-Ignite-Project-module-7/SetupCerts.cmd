@echo off
setlocal enabledelayedexpansion
SET /A counter = 1

where /R "C:\Program Files" keytool.exe > location.txt

call :setsize location.txt 

if %size% lss 5 (
	ECHO I can't find keytool.exe on your computer.  I need keytool.exe to setup the keystores.
	goto :eof
)

REM For each line in file keyfile
for /F "tokens=*" %%L in (location.txt) do (
SET "array[!counter!]=%%L"
ECHO    !counter!: %%L
set /A counter = counter + 1
)
SET /P keytoolLocation="Select which keytool: "
del location.txt

"!array[%keytoolLocation%]!" -genkey -noprompt -alias server-alias -keyalg RSA -keypass changeit -storepass changeit -keystore keystore.jks -dname "CN=Apache Ignite, OU=ID, O=Pluralsight, C=US"
"!array[%keytoolLocation%]!" -export -alias server-alias -storepass changeit -file server.cer -keystore keystore.jks
"!array[%keytoolLocation%]!" -import -noprompt -v -trustcacerts -alias server-alias -file server.cer -keystore cacerts.jks -keypass changeit -storepass changeit
"!array[%keytoolLocation%]!" -list -v -storepass changeit -keystore keystore.jks
"!array[%keytoolLocation%]!" -list -storepass changeit -keystore cacerts.jks

goto :eof

:setsize
set size=%~z1
goto :eof