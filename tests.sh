#!/bin/sh
mvn integration-test
cd tests/stress
mvn clean package
mvn gatling:execute