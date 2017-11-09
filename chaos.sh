#!/bin/sh

excludeIntegration=$(docker ps -aq --filter='name=integration')
excludeDatabase=$(docker ps -aq --filter='name=tcs-database')
table=($(docker ps -aq))
size=${#table[@]}

while true
do
    rand=$((RANDOM % $size))
    while [ ${table[$rand]} == ${excludeIntegration}  ] || [ ${table[$rand]} == ${excludeDatabase} ]
    do
        rand=$((RANDOM % $size))
    done
    nameStop=$(docker ps --filter id=${table[$rand]} --format "{{.Image}}");
    echo "I stop $nameStop"
    docker stop ${table[$rand]}
    sleep 10s
    echo "I restart $nameStop"
    docker start ${table[$rand]}
done