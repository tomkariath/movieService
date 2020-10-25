FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY ./target/movieReview-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch movieReview-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","movieReview-0.0.1-SNAPSHOT.jar"]