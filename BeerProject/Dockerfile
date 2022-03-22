FROM maven:3-openjdk-11 as buildPackage
WORKDIR user/src/beerApp
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY . .
RUN mvn package

FROM openjdk:11
WORKDIR /app
COPY --from=buildPackage /user/src/beerApp/target/beer-project-1.0.jar /user/src/beerApp/scripts/wait-for-it.sh ./
CMD ./wait-for-it.sh db:${DB_PORT} && java -jar beer-project-1.0.jar --spring.profiles.active=container --spring.datasource.username=${DB_USERNAME} --spring.datasource.password=${DB_PASSWORD} --spring.datasource.url="jdbc:mysql://db:${DB_PORT}/${DB_SCHEMA}?useSSL=true&serverTimezone=UTC"
EXPOSE 8081
LABEL profile="container"