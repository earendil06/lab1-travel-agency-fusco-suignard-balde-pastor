#!/bin/sh

#PUSH=true
PUSH=false

build() { # $1: directory, $2: image_name
  cd $1
  docker build -t $2 .
  if [ "$PUSH" = "true" ]; then docker push $2; fi
  cd ../..
}

# Compile services code
mvn clean package

# Build docker images
build services/travel-manager travel-manager
build services/hotel-planner  hotel-planner
build services/car-planner    car-planner
build services/document       tcs-doc