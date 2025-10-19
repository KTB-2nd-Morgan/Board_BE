# 베이스 이미지로 Amazon Corretto 17 (Alpine) 사용
FROM amazoncorretto:17-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 후 생성된 JAR 파일 복사 (빌드 단계에서 이미 jar가 생성되었다고 가정)
# 예: ./build/libs/spring-app.jar 라는 이름의 JAR 파일
COPY build/libs/*.jar app.jar

# 컨테이너가 사용하는 포트 노출 (애플리케이션에 맞게 조정)
EXPOSE 8080

# 컨테이너 시작 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
