#!/bin/bash
cd client
javac -cp ../common:.. ClientConsole.java 
cd ../
java client/ClientConsole