#!/usr/bin/env bash

docker-compose -f docker-compose-integration.yml up -d
sleep 30
docker-compose -f docker-compose-integration.yml run app gradle test
docker-compose -f docker-compose-integration.yml rm -fv