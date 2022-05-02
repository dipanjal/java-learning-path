FROM amazoncorretto:11

COPY target/basic-rest-api-0.0.1-SNAPSHOT.jar /opt/app/basic-rest-api.jar
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "basic-rest-api.jar"]
