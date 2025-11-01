# Usa imagem oficial do Tomcat com Java 17
FROM tomcat:10.1-jdk17

# Define a variável de ambiente PORT (Railway define automaticamente, mas garantimos fallback)
ENV PORT=8080

# Remove o app padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado para o Tomcat
COPY target/LuisKetllyn3infoH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta usada pelo Railway
EXPOSE ${PORT}

# Inicia o Tomcat na porta definida pelo Railway
CMD ["sh", "-c", "catalina.sh run -config /usr/local/tomcat/conf/server.xml & sleep 5 && sed -i 's/8080/'\"$PORT\"'/g' /usr/local/tomcat/conf/server.xml && tail -f /usr/local/tomcat/logs/catalina.out"]
