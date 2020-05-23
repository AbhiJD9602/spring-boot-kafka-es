#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting eureka..."

docker run -d --rm --name eureka \
  -p 8761:8761 --network spring-boot-kafka_default \
      --health-cmd="curl -f http://localhost:8761/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/eureka-server

wait_for_container_log "eureka" "Started"

echo
echo "Starting producer-api..."

docker run -d --rm --name producer-api \
  -p 9084:9084 --network=spring-boot-kafka_default \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 -e EUREKA_HOST=eureka -e ZIPKIN_HOST=zipkin \
  --health-cmd="curl -f http://localhost:9084/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/producer-api

wait_for_container_log "producer-api" "Started"

echo
echo "Starting categorizer-service..."

docker run -d --rm --name categorizer-service \
  -p 9081:9081 --network=spring-boot-kafka_default \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 -e EUREKA_HOST=eureka -e ZIPKIN_HOST=zipkin \
  --health-cmd="curl -f http://localhost:9081/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/categorizer-service

wait_for_container_log "categorizer-service" "Started"

echo
echo "Starting collector-service..."

docker run -d --rm --name collector-service \
  -p 9082:9082 --network=spring-boot-kafka_default \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 -e ELASTICSEARCH_HOST=elasticsearch -e EUREKA_HOST=eureka -e ZIPKIN_HOST=zipkin \
  --health-cmd="curl -f http://localhost:9082/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/collector-service

wait_for_container_log "collector-service" "Started"

echo
echo "Starting publisher-api..."

docker run -d --rm --name publisher-api \
  -p 9085:9085 --network=spring-boot-kafka_default \
  -e ELASTICSEARCH_HOST=elasticsearch -e EUREKA_HOST=eureka -e ZIPKIN_HOST=zipkin \
  --health-cmd="curl -f http://localhost:9085/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/publisher-api

wait_for_container_log "publisher-api" "Started"

echo
echo "Starting news-client..."

docker run -d --rm --name news-client \
  -p 9083:9083 --network=spring-boot-kafka_default \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 -e EUREKA_HOST=eureka -e ZIPKIN_HOST=zipkin \
  --health-cmd="curl -f http://localhost:9083/actuator/health || exit 1" --health-start-period=1m \
  spring.boot.kafka/news-client

wait_for_container_log "news-client" "Started"

printf "\n"
printf "%14s | %37s |\n" "Application" "URL"
printf "%14s + %37s |\n" "--------------" "-------------------------------------"
printf "%14s | %37s |\n" "producer-api" "http://localhost:9084/swagger-ui.html"
printf "%14s | %37s |\n" "publisher-api" "http://localhost:9085/swagger-ui.html"
printf "%14s | %37s |\n" "news-client" "http://localhost:9083"
printf "\n"
