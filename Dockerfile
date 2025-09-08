FROM gradle:8.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
RUN ls -lh build/libs/


FROM eclipse-temurin:17
WORKDIR /app
#jarをdockerイメージ内にコピー
COPY --from=builder /app/build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
RUN ls -lh /app
# 起動時に実行するコマンドを指定
CMD ["java", "-jar", "app.jar"]