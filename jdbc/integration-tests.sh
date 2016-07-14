#!/usr/bin/env bash

docker-compose -f docker-compose-integration.yml run --rm integrationTests
exit=$?
docker-compose -f docker-compose-integration.yml down
exit ${exit}