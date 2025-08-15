FROM eclipse-temurin:17

#jarをdockerイメージ内にコピー
COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
# 起動時に実行するコマンドを指定
ENTRYPOINT ["java", "-jar", "/app.jar"]