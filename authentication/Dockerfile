FROM openjdk:11
ADD ./target/authentication-0.0.1-SNAPSHOT.jar /usr/src/authentication-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "authentication-0.0.1-SNAPSHOT.jar"]