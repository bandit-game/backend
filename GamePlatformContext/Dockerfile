FROM gradle:8.10.2-jdk21 as build
WORKDIR /app
COPY --chown=gradle:gradle settings.gradle.kts buildSrc GamePlatformContext common /app/
RUN gradle :GamePlatformContext:clean :GamePlatformContext:build -x test --no-daemon

FROM openjdk:21-slim as builder
WORKDIR /app
COPY --from=build /app/GamePlatformContext/build/libs/GamePlatformContext.jar /app/GamePlatform.jar
RUN java -Djarmode=layertools -jar GamePlatform.jar extract

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
