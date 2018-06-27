FROM gcr.io/google-appengine/openjdk:8

 COPY target/roadWatch-1.0-SNAPSHOT.jar roadwatch.jar
 COPY config.yml config.yml
 CMD [ "java", "-jar","roadwatch.jar", "server", "config.yml"]
