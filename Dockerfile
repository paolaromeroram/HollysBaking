FROM tomcat:10.1-jdk21

COPY target/HollysBaking-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]