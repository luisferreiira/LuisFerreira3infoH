# Usa imagem oficial do Tomcat com Java 17
FROM tomcat:10.1-jdk17

# Remove o app padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia seu projeto .war para o Tomcat
COPY target/LuisKetllyn3infoH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080
EXPOSE 8080

# Inicia o Tomcat 
CMD ["catalina.sh", "run"]
