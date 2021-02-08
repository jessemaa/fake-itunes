FROM openjdk:15
ADD target/fakeitunes fakeitunesapp.jar
ENTRYPOINT ["java", "-jar", "/fakeitunesapp.jar"]