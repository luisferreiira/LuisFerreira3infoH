# Usa imagem oficial do Tomcat com Java 17
FROM tomcat:10.1-jdk17

# Define variável de ambiente da porta (Railway já define automaticamente)
ENV PORT=8080

# Remove o app padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado pelo Maven (ajuste o nome se for diferente)
COPY target/LuisKetllyn3infoH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta do Tomcat
EXPOSE 8080

# Inicia o Tomcat normalmente
CMD ["catalina.sh", "run"]
