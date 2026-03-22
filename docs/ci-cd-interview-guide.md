# CI/CD Interview Guide

## How My Previous Experience Maps to CI/CD

Your older-company workflow already maps to important CI/CD concepts:

- Pulling production code into test:
  Environment synchronization and release baseline alignment
- Making updates in test first:
  Pre-production validation
- Testing changes in test environment:
  Release verification / QA gate
- Cherry-picking selected commits:
  Controlled promotion of approved changes
- Creating MR for manager review:
  Code review and approval gate
- Merging into master after approval:
  Release branch integration
- Monitoring production after release:
  Post-deployment verification and production monitoring

## CI/CD Terms You Should Know

- Continuous Integration:
  Automatically build and test code changes when they are pushed.
- Continuous Delivery:
  Keep software in a deployable state; production deployment may still require approval.
- Continuous Deployment:
  Automatically deploy every successful change to production.
- Pipeline:
  The automated workflow that runs build, test, package, and deploy stages.
- Build stage:
  Compiles the application and resolves dependencies.
- Test stage:
  Runs unit and integration checks.
- Artifact:
  The output of a build, such as a JAR or Docker image.
- Deployment gate:
  A checkpoint like manual approval before production.
- Rollback:
  Reverting to a previous stable release if an issue is found.
- Smoke test:
  A quick test after deployment to verify the application starts and basic functionality works.

## Interview Answer

Use this answer when asked about CI/CD:

> I have worked within a controlled release workflow even when I was not the main pipeline owner. In my previous role, I first synchronized production code into the test environment, implemented and validated changes there, used cherry-picking to promote selected commits, raised merge requests for review, and merged only after approval. After release, I monitored production behavior to confirm stability. In my current project work, I am also adding GitHub Actions CI pipelines to automate build and test steps for Spring Boot microservices.

## What This Repository Demonstrates

This project now includes a GitHub Actions workflow that:

- checks out the code
- sets up Java 17
- runs Maven tests for each service independently

That is a solid starter CI pipeline for a microservices portfolio project.
