FROM openjdk:11

# HOST PC와 공유할 폴더 설정
VOLUME /tmp

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build


FROM openjdk:11-jre-slim

# 도커 내에서 사용될 변수의 값 설정
ARG JAR_FILE=build/libs/blockai-0.0.1-SNAPSHOT.jar

# JAR_FILE에 해당하는 파일 app.jar로 복사
COPY ${JAR_FILE} app.jar

# CMD와 유사하나, 디폴트로 수행될 명령은 ENTRYPOINT를 사용해 지정
ENTRYPOINT ["java", "-jar", "/app.jar"]