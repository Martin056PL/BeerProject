FROM openjdk:12.0.2-jdk
ADD target/beer-project-1.0.jar .
ADD Docker/wait .
RUN chmod +x /wait
CMD /wait && java -jar beer-project-1.0.jar --spring.profiles.active=container
EXPOSE 8080
LABEL profile="container"