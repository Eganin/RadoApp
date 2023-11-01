FROM openjdk:11
EXPOSE 8081:8081
RUN mkdir /app
COPY build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
RUN mkdir /app/resources
COPY resources/*.jpg /app/resources
RUN mkdir /app/images
RUN mkdir /app/videos
COPY videos/*.mp4 /app/videos