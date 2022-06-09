#!/usr/bin/env bash

set -e

AREA="${1:-planet}"
shift || echo ""

echo "Will build planetiler, download sources for ${AREA}, and make a map."
echo "This requires at least 1GB of disk space. Press Ctrl+C to exit..."
sleep 5

echo "Building..."
./mvnw -DskipTests=true --projects planetiler-dist -am package

echo "Running..."
java -Xmx110g -XX:MaxHeapFreeRatio=40 -jar planetiler-dist/target/*with-deps.jar --force --download-threads=10 --download-chunk-size-mb=1000 --nodemap-type=array --storage=ram --area="${AREA}" $*