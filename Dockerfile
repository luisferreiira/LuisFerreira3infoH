# Usa imagem oficial do Tomcat com Java 17
FROM tomcat:10.1-jdk17

# Remove o app padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia seu projeto .war para o Tomcat
COPY target/LuisKetllyn3infoH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Ajusta a porta do Tomcat para usar a variável de ambiente $PORT do Render
ENV CATALINA_OPTS="-Dport.http=$PORT"

# Expõe a porta dinâmica
EXPOSE $PORT

# Inicia o Tomcat em primeiro plano (não deixa o container parar)
CMD ["catalina.sh", "run"]
