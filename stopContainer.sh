#!/bin/sh

tablo=($(docker ps -a -q))
taille=${#tablo[@]}
rand=$((RANDOM % $taille))
docker stop ${tablo[$rand]}
sleep 10s
docker start ${tablo[$rand]}