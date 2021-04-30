FROM openjdk:15-jdk-alpine
MAINTAINER Daniele Gerbaldo
COPY ./target/scopriamoilpiemontegateway-0.0.1-SNAPSHOT.jar eventi-0.0.1-SNAPSHOT.jar
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","/eventi-0.0.1-SNAPSHOT.jar"]
EXPOSE 80
