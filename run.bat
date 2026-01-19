@echo off
set BIN_DIR=src\bin
set LIB_DIR=lib
set MAIN_CLASS=com.university.Main

echo Running program...
java -cp "%BIN_DIR%;%LIB_DIR%\*" %MAIN_CLASS%
pause

