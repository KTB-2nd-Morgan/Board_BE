#!/bin/bash
# restart.sh - CodeDeploy AfterInstall 후크 스크립트
# 로그 출력 설정
exec > >(tee /var/log/restart.sh.log) 2>&1
set -e  # 스크립트 중간에 에러 나면 중단

echo "Updating system and installing AWS CLI..."
sudo yum update -y
sudo yum install -y awscli

echo "Loading environment variables..."
source /home/ec2-user/app/scripts/set_env.sh

echo "Navigating to app directory"
cd /home/ec2-user/app

echo "Stopping any existing container named spring-app..."
docker stop spring-app || true
docker rm spring-app || true

echo "Logging in to Amazon ECR..."
aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

echo "Pulling latest image from ECR..."
docker pull "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}"

echo "Starting new container from image..."
docker run -d --restart unless-stopped --name spring-app \
 --env-file .env \
 -p 8080:8080 \
 --log-driver=awslogs \
 --log-opt awslogs-group=/morgan/backend/spring-app \
 --log-opt awslogs-region=ap-northeast-2 \
 --log-opt awslogs-stream=spring-container-01 \
 "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}"

echo "✅ Deployment complete."