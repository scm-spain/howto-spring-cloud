#!/usr/bin/env bash

./gradlew clean build
docker-compose -f docker-compose-integration.yml up -d
docker-compose -f docker-compose-integration.yml run --rm app gradle integrationTests
docker-compose -f docker-compose-integration.yml down