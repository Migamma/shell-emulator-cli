#!/usr/bin/env bash

set -e
cd "$(dirname "$0")"

mvn -q clean package

java -jar target/*.jar "$@"
