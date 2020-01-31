#!/bin/bash
cd client
rm *class
javac -cp ../common:.. ClientConsole.java 
cd ../
java client/ClientConsole