# This Dockerfile users the openjdk 17 imange
# Version 1.0.0
# Author: nadav.cheung

# 第一行必须制定基于的基础镜像
#FROM openjdk:17
# === 运行阶段（使用精简、安全的 JDK 17 镜像）===
FROM eclipse-temurin:17-jdk


# 维护者信息
MAINTAINER nadavcheung <nadav.cheung@gmail.com>

WORKDIR /opt/deployments
COPY ./target/binance-connector-client-1.0.0.jar /opt/deployments/

# 环境变量：JVM 内存适配容器资源
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Xmx1024M"

# 暴露端口（可选）
EXPOSE 8080

# 启动时执行的指令
CMD java ${JAVA_OPTS} -jar /opt/deployments/binance-connector-client-1.0.0.jar
