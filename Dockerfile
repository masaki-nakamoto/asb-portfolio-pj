FROM eclipse-temurin:17
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
RUN ls -lh /app/build/libs
#jarをdockerイメージ内にコピー
COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
# app.jar生成確認
RUN ls -lh /app
# 起動時に実行するコマンドを指定
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
