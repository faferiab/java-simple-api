FROM maven:3.9.9-amazoncorretto-17 as builder
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /opt/challenge
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM amazoncorretto:17-alpine3.20-jdk
WORKDIR /opt/challenge
COPY --from=builder /opt/challenge/target/*.jar /opt/challenge/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
