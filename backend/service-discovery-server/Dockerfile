FROM eclipse-temurin:17-jdk-alpine
WORKDIR App
COPY target/*.jar App.jar
EXPOSE 8761
ENTRYPOINT [ "java", "-jar", "App.jar" ]