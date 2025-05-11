FROM openjdk:21

WORKDIR /back-services

COPY ./target/ds-service-app.jar ds-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "ds-service.jar"]
