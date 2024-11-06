@echo off
SETLOCAL

set plugin=hub
set version=1.0
set target=C:\Users\Amari\Desktop\TESTNET\hub\plugins

call .\gradlew shadowJar
copy /y ".\build\libs\%plugin%-%version%-all.jar" "%target%\%plugin%-%version%.jar"
del /f "%target%\config.yml"

ENDLOCAL