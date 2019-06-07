FROM openjdk:8

WORKDIR /work/project

ADD target/jump-api-1.0 /work/project/app.jar

EXPOSE 9004

ENTRYPOINT "java" "-jar" "app.jar"