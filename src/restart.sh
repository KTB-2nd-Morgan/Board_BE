#!/bin/bash
# restart.sh - CodeDeploy AfterInstall 후크 스크립트
# 로그 출력 설정
exec > >(tee /var/log/restart.sh.log) 2>&1

echo "Stopping any existing container named spring-app..."
docker stop spring-app || true
docker rm spring-app || true

echo "Logging in to ECR..."
# AWS CLI가 이미 인스턴스에서 인증되어 있거나, IAM 역할을 통해 인증되는 경우 아래 명령어가 필요하지 않을 수 있습니다.
aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

echo "Pulling latest image from ECR..."
docker pull ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}

echo "Starting new container from image ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}..."
docker run -d --restart unless-stopped --name spring-app -p 8080:8080 \
  ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-app:${IMAGE_TAG}

echo "Deployment complete."