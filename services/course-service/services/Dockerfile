# Use Tomcat 9 running on Java 11
FROM tomcat:9.0-jdk11-temurin

# Optional: clean default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR into Tomcat's webapps folder
COPY target/course-service.war /usr/local/tomcat/webapps/course-service.war

# Tomcat listens on 8080 inside the container
EXPOSE 8080

# Run Tomcat in foreground
CMD ["catalina.sh", "run"]
