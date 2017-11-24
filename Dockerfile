FROM java:8
MAINTAINER Daniel Sularea, Jarek Blaszczyk
ADD target/coverage-tracker-0.1.0-SNAPSHOT.jar /home/coverage-tracker-0.1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/home/coverage-tracker-0.1.0-SNAPSHOT.jar"]