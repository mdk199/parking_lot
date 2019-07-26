#!/bin/sh

mvn clean install
cp target/parking-lot-1.0.0-SNAPSHOT.jar .

if [ $# -eq  0 ]
then
    java -jar parking-lot-1.0.0-SNAPSHOT.jar test_input.txt
else
    java -jar parking-lot-1.0.0-SNAPSHOT.jar $1
fi

rm parking-lot-1.0.0-SNAPSHOT.jar
mvn clean