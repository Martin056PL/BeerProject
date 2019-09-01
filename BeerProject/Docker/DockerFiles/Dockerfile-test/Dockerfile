FROM openjdk:12.0.2-jdk
ADD target/beer-project-1.0.jar .
EXPOSE 8056 
CMD java -jar beer-project-1.0.jar --spring.profiles.active=test
LABEL profile="test"





