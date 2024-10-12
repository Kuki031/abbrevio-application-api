FROM eclipse-temurin:17

LABEL maintainer="kuki031"

WORKDIR /app

COPY ./target/abbrevio-0.0.1-SNAPSHOT.jar /app/abbrevio.jar

ENTRYPOINT ["java", "-jar", "abbrevio.jar"]