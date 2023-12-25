FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

WORKDIR /jeecg-boot

EXPOSE 8080

#ADD ./src/main/resources/jeecg ./config/jeecg
ADD ./target/jeecg-system-start-3.6.1.jar ./

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar jeecg-system-start-3.6.1.jar