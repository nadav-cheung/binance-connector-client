# This Dockerfile users the openjdk 17 imange
# Version 1.0.0
# Author: nadav.cheung

# 第一行必须制定基于的基础镜像
FROM openjdk:17

# 维护者信息
MAINTAINER nadavcheung <nadav.cheung@gmail.com>

WORKDIR /opt/deployments
COPY ./target/binance-connector-client-1.0.0.jar /opt/deployments/
ENV JAVA_OPTS="-Xmx1024M"
EXPOSE 8080

# 启动时执行的指令
CMD java ${JAVA_OPTS} -jar /opt/deployments/binance-connector-client-1.0.0.jar
