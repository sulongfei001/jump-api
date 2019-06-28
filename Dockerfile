FROM    openjdk:8

MAINTAINER  sulongfei

WORKDIR /data/jump_jump

EXPOSE 9004

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

CMD java -Djava.security.egd=file:/dev/./urandom -jar jump-api.jar
