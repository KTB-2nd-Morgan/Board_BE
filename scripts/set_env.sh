#!/bin/bash
echo "Exporting environment variables..."
export AWS_ACCOUNT_ID=418295722497
export AWS_REGION=ap-northeast-2
export IMAGE_TAG=$(cat /home/ec2-user/app/version.txt)  # 또는 적절한 방법으로 IMAGE_TAG를 설정
