#!/bin/bash
cd services
./build.sh
cd ../deployment
docker-compose up -d
