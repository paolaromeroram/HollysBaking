FROM tomcat:10.1-jdk21

# Copiar el WAR
COPY target/HollysBaking-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Configurar puerto dinámico de Railway
RUN printf '#!/bin/bash\nif [ -n "$PORT" ]; then sed -i "s/Connector port="8080"/Connector port="$PORT"/g" /usr/local/tomcat/conf/server.xml; echo "Puerto: $PORT"; fi\ncatalina.sh run\n' > /start.sh && chmod +x /start.sh

EXPOSE 8080

CMD ["/bin/bash", "/start.sh"]