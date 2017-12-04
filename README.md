# coverage-tracker

Application for manage the information about jacoco coverage for other applications

## Docker run
Use docker to run the application. First build with:
	docker build -t coverage-tracker .

Run the application on port 8080:
	docker run -d -p 8080:8080 coverage-tracker

## Docker compose
First build the project, a docker image will be created as well.

Navigate to the root of the project and run:
    docker-compose up

This docker compose expects a file in the root project named `.env` with the following environment
variables in the form of VAR=value separated by newlines: POST_USER, POST_PASS, POST_DB.