FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD target/*.jar Report.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","Report.jar"]