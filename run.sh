#!/usr/bin/env bash

set -e

mvn -q clean package

java -jar target/*.jar "$@"
