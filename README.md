# Quarkus Issue #49431 Reproducer

This repository contains a reproducer for [Quarkus issue #49431](https://github.com/quarkusio/quarkus/issues/49431), which demonstrates a regression in native image builds when using Docker-in-Docker (DinD) environments.

## Issue Summary

Starting with Quarkus 3.25.0, the `--user` flag is no longer automatically added to Docker commands during native image builds in containerized CI environments. This causes permission-related `AccessDeniedException` errors when the container process tries to write build artifacts to directories owned by different users.

The issue specifically affects:
- CI pipelines using Docker-in-Docker
- Environments with specific UID/GID requirements (like UID 645)
- Native image builds using container-based compilation

## Repository Structure

```
├── Dockerfile.reproducer     # Container setup with eclipse-temurin:21-jammy and builder user (UID 645)
├── run-reproducer.sh        # Host script to build and run reproducer
├── reproduce-issue.sh       # Container script that demonstrates the permission issue
├── code-with-quarkus/       # Sample Quarkus application
└── README.md               # This file
```

## Prerequisites

- docker installed on the host system
- Sufficient disk space for container images and Maven dependencies

## Usage

### Quick Start

1. Clone or download this repository
2. Make the run script executable (if not already):
   ```bash
   chmod +x run-reproducer.sh
   ```
3. Execute the reproducer:
   ```bash
   ./run-reproducer.sh
   ```

### What the Reproducer Does

1. **Builds a container** using `eclipse-temurin:21-jammy` as base image
2. **Sets up a builder user** with UID 645 (matching the original issue report)
3. **Installs Docker-in-Docker** capabilities within the container
4. **Runs a Quarkus native build** that should demonstrate the permission issue
5. **Shows verbose output** to help identify when the `--user` flag is missing

### Expected Behavior

In affected Quarkus versions, you should see:
- Docker commands executed without the `--user` flag
- Permission errors when trying to write build artifacts
- Build failures due to `AccessDeniedException`

### Manual Testing

If you want to run the reproducer manually:

```bash
# Build the container
docker build -f Dockerfile.reproducer -t quarkus-issue-49431 .

# Run interactively
docker run --privileged -it quarkus-issue-49431 /bin/bash

# Inside the container, run:
./reproduce-issue.sh
```

## Environment Details

- **Base Image**: `eclipse-temurin:21-jammy`
- **Builder User**: UID 645, GID 645
- **Quarkus Version**: 3.26.1 (contains the regression)
- **Java Version**: 21
- **Maven**: Installed via SDKMAN
- **Docker**: Installed for Docker-in-Docker functionality

## Troubleshooting

### Container Build Issues
- Ensure docker is properly installed and configured
- Check that your user has permission to run docker commands
- Verify internet connectivity for downloading dependencies

### Runtime Issues
- The reproducer requires privileged mode to run Docker-in-Docker
- Some systems may require additional configuration for nested containerization
- SELinux policies might need adjustment on some distributions


This reproducer is designed to help Quarkus developers understand and fix the regression in native image builds.