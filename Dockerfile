FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/compiledir

WORKDIR ${COMPILE_DIR}

#Inside of /app
#Copy files over src destination the dot means current directory ie app
COPY mvnw .
COPY pom.xml .
COPY src src
COPY .mvn .mvn


#Build the application /app/mvn can have multiple run commands
#RUN ./mvnw package -Dmaven.test.skip=true

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

#If build is successful then the jar is in target 
# ./target/week5project-0.0.1-SNAPSHOT.jar

#How to run the application
#ENV SERVER_PORT=8080
ENV PORT=3000
ENV SERVER_PORT={SERVER_PORT}

ENV NOTICEBOARD_DB_HOST=localhost

ENV NOTICEBOARD_DB_PORT=6379

ENV NOTICEBOARD_DB_USERNAME=""

ENV NOTICEBOARD_DB_PASSWORD=""

#What port does the application need
#EXPOSE ${SERVER_PORT}

EXPOSE ${PORT}

#ENTRYPOINT java -jar target/week5project-0.0.1-SNAPSHOT.jar

FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app

WORKDIR ${WORK_DIR}

COPY --from=builder /compiledir/target/day20-0.0.1-SNAPSHOT.jar day20.jar

RUN apt update && apt install -y curl

#ENV SERVER_PORT=8080
ENV PORT=3000
ENV SERVER_PORT={SERVER_PORT}

ENV NOTICEBOARD_DB_HOST=localhost

ENV NOTICEBOARD_DB_PORT=6379

ENV NOTICEBOARD_DB_USERNAME=""

ENV NOTICEBOARD_DB_PASSWORD=""

#What port does the application need
#EXPOSE ${SERVER_PORT}

EXPOSE ${PORT}

#ENTRYPOINT java -jar target/week5project-0.0.1-SNAPSHOT.jar

ENTRYPOINT java -jar day20.jar

HEALTHCHECK CMD curl -s -f <health status URL> || exit 1
