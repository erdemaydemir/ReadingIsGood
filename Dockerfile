FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
ENV JAVA_OPTS='-Xms2048M -Xmx4096M'
ARG JAR_FILE='/home/app/target/*.jar'
WORKDIR /opt/app
COPY --from=build ${JAR_FILE} ms.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -jar ms.jar"]