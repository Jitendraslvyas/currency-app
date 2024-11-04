FROM openjdk:11
ADD target/currency-app.jar currency-app.jar
ENTRYPOINT ["java", "-jar","currency-app.jar"]
EXPOSE 8081
