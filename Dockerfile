FROM openjdk:8
EXPOSE 8080
ADD target/spring-ci-cd-test.jar spring-ci-cd-test.jar
ENTRYPOINT ["java","-jar","/spring-ci-cd-test.jar"]