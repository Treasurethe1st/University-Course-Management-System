#!/bin/bash
BIN_DIR="src/bin"
LIB_DIR="lib"
MAIN_CLASS="com.university.Main"

echo "Running program..."
java -cp "$BIN_DIR:$LIB_DIR/*" "$MAIN_CLASS"

