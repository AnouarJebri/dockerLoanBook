FROM openjdk:17-jdk-alpine
WORKDIR /appv1
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /appv1/api-gateway-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/appv1/api-gateway-0.0.1-SNAPSHOT.jar"]
EXPOSE 8087