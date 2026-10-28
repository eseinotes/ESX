FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn -B dependency:go-offline

COPY src ./src

RUN mvn -B -DskipTests package dependency:copy-dependencies \
    -DoutputDirectory=target/dependency

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/classes ./classes
COPY --from=build /app/target/dependency ./lib

EXPOSE 8080

CMD ["java", "-cp", "classes:lib/*", \
    "edu.example.studentperformance.Main"]