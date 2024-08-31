@echo off
setlocal enabledelayedexpansion

rem Set variables from arguments
set "JavaFXPath=%~1"
set "JavaFXModules=%~2"
set "SrcPath=%~3"
set "BinPath=%~4"

rem Print paths and modules for debugging
echo FxPath: %JavaFXPath%
echo FxModules: %JavaFXModules%
echo SrcPath: %SrcPath%
echo binPath: %BinPath%

rem Initialize a variable to hold all Java files
set "allJavaFiles="

rem Find all Java files in the source path and add them to the variable
for /R "%SrcPath%" %%f in (*.java) do (
    set "allJavaFiles=!allJavaFiles! %%f"
    
)
echo %allJavaFiles%
rem Compile all Java files in one command
javac --module-path "%JavaFXPath%" --add-modules %JavaFXModules% -d "%BinPath%" %allJavaFiles%

rem Notify the user about the completion
echo Compilation finished with exit code %errorlevel%.

endlocal
