#!/bin/bash

set -e

echo "=== Quarkus Issue #49431 Reproducer ==="
echo "Testing native image build in Docker-in-Docker environment"
echo "Expected: Should fail with AccessDeniedException due to missing --user flag"
echo ""

# # Start Docker daemon in background
echo "Starting Docker daemon..."
sudo dockerd &
DOCKER_PID=$!

# Wait for Docker to be ready
echo "Waiting for Docker to be ready..."
until docker info > /dev/null 2>&1; do
    sleep 1
done

echo "Docker is ready!"
echo ""

# Show current user info
echo "Current user info:"
id
echo ""

# Build native image using container build
echo "Building native image with container build..."
echo "This should demonstrate the permission issue when --user flag is missing"
echo ""

cd /home/builder/code-with-quarkus

# Enable verbose logging to see Docker commands
export QUARKUS_LOG_LEVEL=DEBUG

# Attempt native build with container
./mvnw clean package -Pnative \
    -Dquarkus.native.container-build=true

echo ""
echo "Build completed successfully!"

# Cleanup
kill $DOCKER_PID 2>/dev/null || true