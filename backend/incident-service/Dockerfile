FROM eclipse-temurin:17-jdk-alpine
WORKDIR App
COPY target/*.jar App.jar
ARG DEFAULT_PORT=8085
ENV PORT $DEFAULT_PORT
EXPOSE $PORT
ENTRYPOINT [ "java", "-jar", "App.jar" ]

