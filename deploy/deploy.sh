#!/bin/bash

# 检查容器是否存在，存在则停止并删除
if sudo docker ps -a --format '{{.Names}}' | grep -w "love-boot" &>/dev/null; then
  echo "停止并移除现有的容器 love-boot"
  sudo docker stop love-boot
  sudo docker rm love-boot
else
  echo "容器 love-boot 不存在，无需停止或删除"
fi

# 构建新的 Docker 镜像
echo "构建新的 Docker 镜像 love-boot"
sudo docker build -t love-boot .

# 删除 config、logs 和 upload 文件夹
echo "删除 config、logs 和 upload 文件夹"
sudo rm -rf config/
sudo rm -rf logs/
sudo rm -rf upload/

# 启动新的 Docker 容器
echo "启动新的容器 love-boot"
sudo docker run -d -p 8101:8080 \
  -v /usr/local/bin/project/love/upload:/app/upload \
  -v /usr/local/bin/project/love/logs:/app/logs \
  --restart always --name love-boot love-boot

echo "操作完成"
