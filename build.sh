#!/bin/sh

#PUSH=true
PUSH=false

build() { # $1: directory, $2: image_name
  cd $1
  docker build -t $2 .
  if [ "$PUSH" = "true" ]; then docker push $2; fi
  cd ../..
}

cd ./deployment/
mkdir camel_input
mkdir camel_output
cd ..

# Compile services code
mvn clean package

# Build docker images
#build services/travel-manager travel-manager
#build services/hotel-planner  hotel-planner
#build services/car-planner    car-planner
#build services/document       tcs-doc

#build integration/esbgroupe4 esbgroupe4
cd integration/esbgroupe4
docker build -t esbgroupe4 . #--no-cache
cd ../..

cd integration
docker build -t integration . #--no-cache
cd ..

#our group
docker pull earendil06/our-flights
docker pull earendil06/our-hotels
docker pull earendil06/our-cars

#other groups
docker pull earendil06/other-flights
docker pull earendil06/other-hotels
docker pull earendil06/other-cars