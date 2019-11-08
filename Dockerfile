FROM openjdk:11-jre-slim

ENV APPLICATION list-keeper.jar
ENV APPLICATION_USER ktor
RUN adduser --disabled-login --disabled-password --gecos '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/$APPLICATION /app/$APPLICATION
WORKDIR /app

CMD java -server -XX:+UnlockExperimentalVMOptions -XX:InitialRAMFraction=2 -XX:MinRAMFraction=2 -XX:MaxRAMFraction=2 -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication -jar $APPLICATION
