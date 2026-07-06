FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk21

COPY --from=build /app/target/HollysBaking-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

RUN printf '#!/bin/bash\nif [ -n "$PORT" ]; then sed -i "s/Connector port="8080"/Connector port="$PORT"/g" /usr/local/tomcat/conf/server.xml; fi\ncatalina.sh run\n' > /start.sh && chmod +x /start.sh

EXPOSE 8080

CMD ["/bin/bash", "/start.sh"]