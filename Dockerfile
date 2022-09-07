FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} manoamiga.jar
ENTRYPOINT ["java","-jar","/manoamiga.jar"]

