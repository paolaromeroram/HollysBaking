#!/bin/bash

if [ -n "$PORT" ]; then
    sed -i "s/port=\"8080\"/port=\"$PORT\"/g" /usr/local/tomcat/conf/server.xml
    echo "Tomcat configurado para puerto $PORT"
else
    echo "Usando puerto por defecto 8080"
fi

catalina.sh run
