FROM adoptopenjdk/openjdk8-openj9:slim

ENV ENV docker

WORKDIR /opt/redrule

ENTRYPOINT ["java", "-Xmx64m", "-jar", "redrule.jar"]

COPY bot/build/libs/redrule.jar /opt/redrule/redrule.jar
