#!/bin/bash
echo 'Logger: Run BeerApp base on dockerfile'
echo 'Logger: Stopping running container'
docker stop beerApi

echo 'Logger: Removing existing container'
docker rm beerApi

echo 'Logger: Removing beerapi image'
docker image rm beerapi:latest

echo 'Logger: Create beerapi image'
docker build . -t beerapi:latest

echo 'Logger: Create and run container'
docker run -p 8081:8086 --name beerApi beerapi:latest