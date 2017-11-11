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
    sleep 5s

    rand=$((RANDOM % $size))
    while [ ${table[$rand]} == ${excludeIntegration}  ] || [ ${table[$rand]} == ${excludeDatabase} ]
    do
        rand=$((RANDOM % $size))
    done
    nameStart=$(docker ps --filter id=${table[$rand]} --format "{{.Image}}");
    echo "I start $nameStart"
    docker start ${table[$rand]}
    sleep 5s
done