#!/bin/sh

tablo=($(docker ps -aq | grep -v $(docker ps -q --filter='name=integration')))
taille=${#tablo[@]}
rand=$((RANDOM % $taille))
docker stop ${tablo[$rand]}
sleep 10s
docker start ${tablo[$rand]}