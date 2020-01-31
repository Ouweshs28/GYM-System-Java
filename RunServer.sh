#!/bin/bash
cd server
javac -cp ../common:.. Server.java 
cd ../
java server/Server