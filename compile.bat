@echo off
REM Project paths
set SRC_DIR=src\com\university
set BIN_DIR=src\bin
set LIB_DIR=lib

REM Create bin folder if it doesn't exist
if not exist %BIN_DIR% mkdir %BIN_DIR%

echo Compiling Java files...

REM Compile all .java files recursively
for /R %SRC_DIR% %%f in (*.java) do (
    javac -d %BIN_DIR% -cp "%LIB_DIR%\*" "%%f"
)

echo Compilation finished!
pause

