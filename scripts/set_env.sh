#!/bin/bash
echo "Exporting environment variables..."
export AWS_ACCOUNT_ID=418295722497
export AWS_REGION=ap-northeast-2
export IMAGE_TAG=latest  # 또는 적절한 방법으로 IMAGE_TAG를 설정

echo "Fetching DB credentials from AWS SSM Parameter Store..."

export DB_URL=$(aws ssm get-parameter --name "/morgan/dev/db/url" --with-decryption --query "Parameter.Value" --output text)
export DB_USERNAME=$(aws ssm get-parameter --name "/morgan/dev/db/username" --with-decryption --query "Parameter.Value" --output text)
export DB_PASSWORD=$(aws ssm get-parameter --name "/morgan/dev/db/password" --with-decryption --query "Parameter.Value" --output text)
export DB_NAME=$(aws ssm get-parameter --name "/morgan/dev/db/name" --query "Parameter.Value" --output text)

cat <<EOF > /home/ec2-user/app/.env
DB_URL=$DB_URL
DB_USERNAME=$DB_USERNAME
DB_PASSWORD=$DB_PASSWORD
DB_NAME=$DB_NAME
EOF

echo ".env file created successfully."