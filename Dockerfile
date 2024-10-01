FROM openjdk:21-jdk AS build

VOLUME /tmp

COPY target/restaurante-0.0.1-SNAPSHOT.jar tech_rest.jar

EXPOSE 8080/tcp
EXPOSE 8080/udp

ENTRYPOINT ["java", "-jar", "/tech_rest.jar"]