# Usa una versione specifica di Jetty compatibile con Java 8
FROM jetty:9.4-jdk8-openjdk

# Imposta variabili d'ambiente per il database (opzionale, possono essere override)
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=todoapp
ENV DB_USER=root
#ENV DB_PASSWORD=password

# Copia il file WAR nella directory webapps di Jetty
# Se vuoi che sia accessibile su /webapptest, mantieni il nome originale
COPY ./target/webapptest.war /var/lib/jetty/webapps/webapptest.war

# Oppure se vuoi che sia il contesto radice (/), usa ROOT.war
# COPY ./target/webapptest.war /var/lib/jetty/webapps/ROOT.war

# Espone la porta 8080 (sintassi corretta)
EXPOSE 8080

# Opzionale: configura JVM options per ottimizzare le performance
ENV JAVA_OPTIONS="-Xmx512m -Xms256m"

# Il comando di default dell'immagine Jetty è sufficiente
# CMD viene ereditato dall'immagine base