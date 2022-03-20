FROM maven:3-openjdk-11 as build
WORKDIR user/src/beerApp
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY . .
RUN mvn package

FROM openjdk:11
WORKDIR /app
COPY --from=build /user/src/beerApp/target/beer-project-1.0.jar .
COPY --from=build /user/src/beerApp/Docker/wait-for-it.sh .
CMD ./wait-for-it.sh db:3306 && java -jar beer-project-1.0.jar --spring.profiles.active=container
EXPOSE 8081
LABEL profile="container"