FROM openjdk:21

LABEL authors="Maverick"

# 系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

# 设置工作目录
WORKDIR /app

# 将项目的 JAR 包复制到镜像中
COPY love-boot.jar /app/app.jar

# 将默认头像复制到镜像中
COPY default.jpg /app/upload/image/avatar/default.jpg

# 设置 Alpine 系统时区
ENV TZ=Asia/Shanghai

# 暴露项目运行的端口
EXPOSE 8080

# 设置环境变量
ENV JAVA_OPTS="-Xmx256m -Xms128m"

# 容器启动时执行的命令
CMD ["java", "-jar", "/app/app.jar"]
