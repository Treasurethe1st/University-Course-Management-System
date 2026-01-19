#!/bin/bash
SRC_DIR="src"
BIN_DIR="src/bin"
LIB_DIR="lib"

# Create bin folder if it doesn't exist
mkdir -p "$BIN_DIR"

echo "Compiling Java files..."

# Find all Java files recursively inside src/com/university and subfolders
JAVA_FILES=$(find "$SRC_DIR/com/university" -type f -name "*.java")

# Check if any files were found
if [ -z "$JAVA_FILES" ]; then
    echo "No Java source files found in $SRC_DIR/com/university!"
    exit 1
fi

# Compile all Java files into bin
javac -d "$BIN_DIR" -cp "$LIB_DIR/*" $JAVA_FILES

echo "Compilation finished!"

