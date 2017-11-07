#!/bin/sh

tablo=($(docker ps -a -q))
taille=${#tablo[@]}
rand=$((RANDOM % $taille))
docker stop ${tablo[$rand]} 
#docker rm ${tablo[$rand]}
