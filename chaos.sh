#!/bin/sh

exclude=$(docker ps -q --filter='name=integration')
tablo=($(docker ps -aq))
taille=${#tablo[@]}

while true
do
    rand=$((RANDOM % $taille))
    while [ ${tablo[$rand]} -eq $exclude ]
    do
        rand=$((RANDOM % $taille))
    done

    docker stop ${tablo[$rand]}
    sleep 10s
    docker start ${tablo[$rand]}
done