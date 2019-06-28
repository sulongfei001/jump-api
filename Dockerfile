FROM    openjdk:8

MAINTAINER  sulongfei

WORKDIR /work/jump_jump

ADD ./jump-api-1.0.jar /work/jump_jump/app.jar

EXPOSE 9004

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT "java" "-Djava.security.egd=file:/dev/./urandom" "-jar" "app.jar"
