#!/bin/bash

#Add libraries to classpath
tinyos=/opt/tinyos-2.1.2/support/sdk/java/tinyos.jar
sqlitedb=../lib/sqlite-jdbc-3.8.7.jar

#config files
motes=../../configs/motes.csv
scenario=../../configs/scenario.csv

#compile
echo "Compiling..."
javac -cp .:$sqlitedb:$tinyos rssi/*.java rssi/config/*.java rssi/db/*.java rssi/message/*.java

#run
echo "Executing RssiBase $motes $scenario"
java -cp .:$sqlitedb:$tinyos rssi/RssiBase $motes $scenario

