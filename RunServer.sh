#!/bin/bash
cd server
rm *class
javac -cp ../common:.. Server.java 
cd ../
java server/Server