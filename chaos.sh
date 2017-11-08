#!/bin/sh

tablo=($(docker ps -aq | grep -v $(docker ps -q --filter='name=integration')))
taille=${#tablo[@]}

while true
do
    rand=$((RANDOM % $taille))
    docker stop ${tablo[$rand]}
    sleep 10s
    docker start ${tablo[$rand]}
done