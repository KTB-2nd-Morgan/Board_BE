#!/bin/bash
# restart.sh - CodeDeploy AfterInstall 후크 스크립트
# 로그 출력 설정
exec > >(tee /var/log/restart.sh.log) 2>&1

yum update
yum install awscli -y

echo "Stopping any existing container named spring-app..."
docker stop spring-app || true
docker rm spring-app || true

echo "Logging in to Amazon ECR..."
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app

echo "Pulling latest image from ECR..."
docker pull ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}

echo "Starting new container from image ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}..."
docker run -d --restart unless-stopped --name spring-app -p 8080:8080 \
  ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}

echo "Deployment complete."