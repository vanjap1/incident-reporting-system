FROM eclipse-temurin:17-jdk-alpine
WORKDIR App
COPY target/*.jar App.jar
EXPOSE 8888
ENTRYPOINT [ "java", "-jar", "App.jar" ]