:: #@author: Simone Bianco

@echo off
setlocal enabledelayedexpansion

SET HELP=0
SET RUN=0
SET JAR=0
SET VERBOSE=0

:parse_args
IF NOT "%1"=="" (
    IF "%1"=="-h" SET HELP=1
    IF "%1"=="--help" SET HELP=1

    IF "%1"=="-r" SET RUN=1
    IF "%1"=="--run" SET RUN=1

    IF "%1"=="-j" SET JAR=1
    IF "%1"=="--jar" SET JAR=1

    IF "%1"=="-v" SET VERBOSE=1
    IF "%1"=="--verbose" SET VERBOSE=1

    SHIFT
    GOTO parse_args
)

IF %HELP% == 1 (
    GOTO display_help
)

GOTO compile
:return_compile

IF %JAR% == 1 (
    GOTO create_jar
)
:return_create_jar

IF %RUN% == 1 (
    GOTO run_program
)
:return_run_program

GOTO end

:: ####################
:: # Display the help command
:: ####################

:display_help
	echo NOTE: Automatically compiles all classes and libraries used in this project
	echo.

	echo Usage: compile [-h] [-j] [-r] [-v]
	echo.

	echo Optional operations:
	echo     -h, --help:          Show this help message
	echo     -j, --jar:           Create independent, portable and runnable JAR file
	echo     -r, --run:           Run program after compilation
	echo     -v, --verbose:       Show more output

    GOTO end

:: ####################
:: # Main class compilation process
:: ####################

:compile
	
	:: # Create build directory

	echo Creating build directory...
	mkdir build

	:: # Instantiate empty classpath 
	echo Building import classpath...

	:: # Add every source file found in src/ to classpath
	SET "sources_paths=src/*;src/"

    FOR /F %%G IN ('dir src /S /B /Ad') DO (

        :: From absolute path to relative path
        SET "relative_path=%%G"
        SET "relative_path=!relative_path:%CD%\=!"
        SET "relative_path=!relative_path:\=/!"

        IF %VERBOSE% == 1 (
            echo Including !relative_path!...
        )

        SET "sources_paths=!sources_paths!;!relative_path!"
    )

    :: # Add every library found in lib/ to classpath
    SET "libraries_paths=lib/*;lib/"

    FOR /F %%G IN ('dir lib /S /B /A-d') DO (

        :: From absolute path to relative path
        SET "relative_path=%%G"
        SET "relative_path=!relative_path:%CD%\=!"
        SET "relative_path=!relative_path:\=/!"

        IF %VERBOSE% == 1 (
            echo Including !relative_path!...
        )

        SET "libraries_paths=!libraries_paths!;!relative_path!"
    )

	:: # Compile classes using classpath
	echo.
	echo Compiling classes...

	SET "classpath=%sources_paths%;%libraries_paths%"

	javac -proc:none -target 1.8 -source 1.8 -d build -cp "%classpath%" src\app\Main.java

	echo Compilation completed.

	:: # Copy assets in build directory
	echo.
	echo Copying assets into build directory...

	xcopy src\assets\ build\assets\ /E /Y > nul

    GOTO return_compile

:create_jar

    SET "start_dir=%CD%"
    SET "jar_build_dir=jar_build"

    mkdir %jar_build_dir%

    echo Evaluating classes to be hooked into JAR file...

    echo Hooking built project classes...
    xcopy build\ %jar_build_dir%\ /E /Y > nul

    echo Hooking project libraries...

    FOR /F %%G IN ('dir lib /S /B /A-d') DO (
        IF %VERBOSE% == 1 (
            echo Hooking %%G...
        )

        :: # Copy file to build dir
        copy %%G %jar_build_dir% /Y > nul
        cd %jar_build_dir%
        
        SET basename=%%~nxG

        :: # Extract and delete jar file
        jar -xf !basename!
        del !basename!

        cd %start_dir%
    )

    :: # Compile JAR
    echo.
    echo Creating JAR file...
    jar -cfe BloodlineSeeker.jar app/Main -C %jar_build_dir%/ .

    :: Clear building directory
    echo Cleaning hooked files...
    rd /S /Q %jar_build_dir%

    echo JAR file created successfully
    echo.
    GOTO return_create_jar

:: ####################
:: ## Run program after compilation
::####################

:run_program
:: # Run arguments

    echo Running compiled program...
    echo.
    
    SET "builds_paths=build/*;build/"

    FOR /F %%G IN ('dir src /S /B /Ad') DO (

        :: From absolute path to relative path
        SET "relative_path=%%G"
        SET "relative_path=!relative_path:%CD%\=!"
        SET "relative_path=!relative_path:\=/!"

        SET "builds_paths=!builds_paths!;!relative_path!"
    )

    SET classpath="%builds_paths%:%libraries_paths%"

    java -cp "%classpath%" app.Main

    GOTO return_run_program

:end  
    EXIT /B 0