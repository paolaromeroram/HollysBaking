# Forzar compilación limpia cada vez
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Forzar compilación limpia (sin caché)
RUN mvn clean package -DskipTests

# Etapa final: Tomcat
FROM tomcat:10.1-jdk21

# Limpiar webapps para evitar caché
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el WAR generado
COPY --from=build /app/target/HollysBaking-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Configurar puerto dinámico
RUN printf '#!/bin/bash\nif [ -n "$PORT" ]; then sed -i "s/Connector port="8080"/Connector port="$PORT"/g" /usr/local/tomcat/conf/server.xml; fi\ncatalina.sh run\n' > /start.sh && chmod +x /start.sh

EXPOSE 8080

CMD ["/bin/bash", "/start.sh"]