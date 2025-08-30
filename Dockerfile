FROM eclipse-temurin:17
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
RUN ls -lh build/libs/
#jarをdockerイメージ内にコピー
# COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
RUN ls -lh build/libs/spring-0.0.1-SNAPSHOT.jar
# 起動時に実行するコマンドを指定
CMD ["java", "-jar", "app.jar"]