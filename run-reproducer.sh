#!/bin/bash

set -e

echo "=== Quarkus Issue #49431 Reproducer ==="
echo ""

# Build the Docker image
echo "Building reproducer image..."
docker build -f Dockerfile.reproducer -t quarkus-issue-49431 .

echo ""
echo "Running reproducer container with privileged access..."
echo "This will demonstrate the permission issue in Docker-in-Docker environment"
echo ""

# Run the reproducer with Docker-in-Docker capability
docker run --rm --privileged \
    quarkus-issue-49431 \
    ./reproduce-issue.sh

echo ""
echo "Reproducer completed!"