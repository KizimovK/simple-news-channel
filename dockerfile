FROM openjdk:17-oracle
ENV HOME=/app
WORKDIR $HOME
COPY build/libs/simple-news-channel-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]