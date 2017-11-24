# coverage-tracker

Application for manage the information about jacoco coverage for other applications

## Docker run
Use docker to run the application. First build with:
	docker build -t coverage-tracker .

Run the application on port 8080:
	docker run -d -p 8080:8080 coverage-tracker
