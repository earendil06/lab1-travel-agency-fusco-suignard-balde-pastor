#!/bin/sh

./sendInput.sh ./input/input1.txt 1
./sendInput.sh ./input/input1.txt 2
./sendInput.sh ./input/input1.txt 3

sleep 5s

./sendInput.sh ./input/noFlight.txt 4