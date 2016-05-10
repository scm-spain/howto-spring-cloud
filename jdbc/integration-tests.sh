#!/usr/bin/env bash

docker-compose -f docker-compose-integration.yml up -d
docker-compose -f docker-compose-integration.yml run --rm app gradle dockerIntegrationTests
exit=$?
docker-compose -f docker-compose-integration.yml down
exit ${exit}