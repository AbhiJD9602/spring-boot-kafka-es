#!/usr/bin/env bash

./gradlew :categorizer-service:build            #./gradlew clean build do not work when we have common module without main class
./gradlew :collector-service:build
./gradlew :eureka-server:build
./gradlew :news-client:build
./gradlew :producer-api:build
./gradlew :publisher-api:build


set -eo pipefail

modules=( categorizer-service collector-service eureka-server news-client producer-api publisher-api )

for module in "${modules[@]}"; do
    docker build -t "spring.boot.kafka/${module}:latest" ${module}
done