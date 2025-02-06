#!/bin/bash

# 设置路径变量
BASE_PATH="/usr/local/bin/project/love"
UPLOAD_PATH="$BASE_PATH/upload"
LOGS_PATH="$BASE_PATH/logs"
CONFIG_PATH="$BASE_PATH/config"

# 检查容器是否存在，存在则停止并删除
if sudo docker ps -a --format '{{.Names}}' | grep -w "love-boot" &>/dev/null; then
  echo "停止并移除现有的容器 love-boot"
  sudo docker stop love-boot && sudo docker rm love-boot
else
  echo "容器 love-boot 不存在，无需停止或删除"
fi

# 构建新的 Docker 镜像
echo "构建新的 Docker 镜像 love-boot"
if ! sudo docker build -t love-boot .; then
  echo "镜像构建失败！"
  exit 1
fi

# 启动新的 Docker 容器
echo "启动新的容器 love-boot"
if ! sudo docker run -d -p 8101:8080 \
  -v "$UPLOAD_PATH:/app/upload" \
  -v "$LOGS_PATH:/app/logs" \
  --restart always --name love-boot love-boot; then
  echo "容器启动失败！"
  exit 1
fi

# 创建目录结构并添加默认文件
echo "创建目录结构并添加默认文件"
sudo mkdir -p "$UPLOAD_PATH/image/reward" \
  "$UPLOAD_PATH/image/avatar" \
  "$UPLOAD_PATH/image/background" \
  "$UPLOAD_PATH/image/poop" \
  "$UPLOAD_PATH/logs"

# 复制默认图片
sudo cp default.jpg "$UPLOAD_PATH/image/avatar/default.jpg"

echo "操作完成"