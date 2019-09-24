FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY target/influx-web-proxy-0.1.jar /opt/app/app.jar
WORKDIR /opt/app
CMD ["java", "-jar", "/opt/app/app.jar"]