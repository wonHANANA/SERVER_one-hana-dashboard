FROM amazoncorretto:17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test
 #./gradlew clean build --no-daemon

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/src/main/java/com/onehana/onehanadashboard/crawling/driver/forUbuntu/chromedriver_linux64/ chromedriver_linux64/
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]