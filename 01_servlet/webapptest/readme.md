 mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.5

<!-- Aggiungi questo plugin nella sezione <plugins> dentro <build> -->
<plugin>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-maven-plugin</artifactId>
    <version>9.4.54.v20240208</version>
    <configuration>
        <scanIntervalSeconds>10</scanIntervalSeconds>
        <webApp>
            <contextPath>/webapptest</contextPath>
        </webApp>
    </configuration>
</plugin>

<!-- Aggiungi queste dipendenze nella sezione <dependencies> -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.3</version>
    <scope>provided</scope>
</dependency>

