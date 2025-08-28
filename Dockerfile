# マルチステージ
# 1.ビルド用
FROM gradle:8.1-jdk17 AS builder
WORKDIR /app
COPY . .
# デプロイ時にrenderにbuildさせる
RUN ./gradlew clean build -x test
# spring-0.0.1-SNAPSHOT.jar生成確認
RUN ls -lh /app/build/libs

# 実行用
FROM eclipse-temurin:17
WORKDIR /app
#jarをdockerイメージ内にコピー
COPY --from=builder /app/build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
# app.jar生成確認
RUN ls -lh /app
EXPOSE 8080
# 起動時に実行するコマンドを指定
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
