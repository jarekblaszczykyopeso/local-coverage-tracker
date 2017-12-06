# coverage-tracker

Application for manage the information about jacoco coverage for other applications

## Docker compose
First build the project, a docker image will be created as well. For build the project docker should run on your machine.

Navigate to the root of the project and run:
    docker-compose up

This docker compose expects a file in the root project named `.env` with the following environment
variables in the form of VAR=value separated by newlines: POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB.
