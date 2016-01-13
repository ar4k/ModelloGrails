#!/bin/bash
# Compila il jar
#compila i file statici di angular material
origine=$(pwd)
cd web-app
bower install
cd $origine
./grailsw prod build-standalone -Dbuild.compiler=javac1.7
#mv target/standalone-0.1.jar ar4k.jar
