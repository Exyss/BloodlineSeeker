#!/bin/bash
#@author: Simone Bianco


####################
# Display the help command
####################

display_help(){
	echo "Info: Automatically compile all classes and libraries used in this project"
	echo ""

	echo "Usage: compile [-h] [-j] [-r] [-v]"
	echo ""

	echo "Optional operations:"
	echo "    -h | --help: Show the help menu"
	echo "    -j | --jar: Create independent, portable and runnable JAR file"
	echo "    -r | --run: Run program after compilation"
	echo "    -v | --verbose: Show more output"
}


####################
# Main class ompilation process
####################

compile(){
	
	# Create build directory

	echo "Creating build directory..."
	mkdir -p build


	# Instantiate empty classpath 
	echo "Building import classpath..."

	# Add every source file found in src/ to classpath
	sources_paths="src/*"
	sources=$(find src -maxdepth 1 -type d)

	for file in $sources
	do
		if [[ $VERBOSE == 1 ]]; then
			echo "Including $file..."
		fi

		sources_paths="$sources_paths:$file"
	done

	# Add every library found in lib/ to classpath
	libraries_paths="lib/*"
	libraries=$(find lib -type f)

	for file in $libraries
	do	
		if [[ $VERBOSE == 1 ]]; then
					echo "Including $file..."
			fi

		libraries_paths="$libraries_paths:$file"
	done

	# Compile classes using classpath
	echo ""
	echo "Compiling classes..."

	classpath="$sources_paths:$libraries_paths"

	javac -proc:none -target 1.8 -source 1.8 -d build -cp \"$classpath\" src/app/Main.java

	echo "Compilation completed."

	# Copy assets in build directory
	echo ""
	echo "Copying assets into build directory..."

	cp -r src/assets build
}

####################
## Hook everything inside a single runnable JAR file
####################

create_jar(){
	# Create JAR file

	if [[ $JAR == 1 ]]; then
		
		# Create temporary directory
		start_dir=$(pwd)
		jar_build_dir="jar_build"

		mkdir -p $jar_build_dir

		echo "Evaluating classes to be hooked into JAR file..."

		echo "Hooking built project classes..."
		cp -r build/* $jar_build_dir
		
		echo "Hooking project libraries..."
		for file in $libraries
		do
			if [[ $VERBOSE == 1 ]]; then
				echo "Hooking $file..."
			fi

			cp $file $jar_build_dir
			cd $jar_build_dir
			
			jar -xf $(basename $file)
			rm $(basename $file)

			cd $start_dir
		done

		# Compile JAR
		echo ""
		echo "Creating JAR file..."
		jar -cfe BloodlineSeeker.jar app/Main -C $jar_build_dir/ .
		
		echo "Cleaning hooked files..."
		rm -rf $jar_build_dir
		
		echo "JAR file created successfully"
		echo ""
	fi
}

####################
## Run program after compilation
####################

run_program(){
	# Run arguments

	if [[ $RUN == 1 ]]; then
		echo "Running compiled program..."
		echo ""
		
		builds_paths="build/*"
		builds=$(find build -maxdepth 1 -type d)

		for file in $builds
		do
				builds_paths="$builds_paths:$file"
		done

		classpath="$builds_paths:$libraries_paths"
		java -cp \"$classpath\" app.Main
	fi
}

####################
## Main
####################

# Check bonus arguments

HELP=0
RUN=0
JAR=0
VERBOSE=0

while [ "$1" != "" ]; do
    case $1 in
    -h | --help)
	HELP=1
	;;

    -r | --run)
        RUN=1
        ;;

    -j | --jar)
        JAR=1
        ;;

    -v | --verbose)
        VERBOSE=1
        ;;

    *)
        ;;
    esac
    shift # remove the current value for `$1` and use the next
done

if [[ $HELP == 1 ]]; then
	display_help
	exit 0
fi

compile

if [[ $JAR == 1 ]]; then
	create_jar
	exit 0
fi
if [[ $RUN == 1 ]]; then
	run_program
	exit 0
fi
