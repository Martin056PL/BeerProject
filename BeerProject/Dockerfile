FROM maven:3-openjdk-11-slim as buildPackage
WORKDIR user/src/beerApp
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY . .
RUN mvn package

FROM openjdk:11.0-jre-slim
WORKDIR /app
COPY --from=buildPackage /user/src/beerApp/target/beer-project-1.0.jar /user/src/beerApp/scripts/wait-for-it.sh ./
CMD ./wait-for-it.sh db:3306 && java -jar beer-project-1.0.jar --spring.profiles.active=container --spring.datasource.username=${DB_USERNAME} --spring.datasource.password=${DB_PASSWORD} --spring.datasource.url="jdbc:mysql://db:3306/${DB_SCHEMA}?useSSL=true&serverTimezone=UTC"
EXPOSE ${API_PORT}
LABEL profile="container"