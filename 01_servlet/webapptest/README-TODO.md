# WebApp Test - Todo List con Hibernate

Applicazione web Java che utilizza Hibernate per gestire una lista di Todo Items con database MySQL.

## Struttura del Progetto

```
src/
├── main/
│   ├── java/
│   │   └── it/eforhum/
│   │       ├── entity/
│   │       │   └── TodoItem.java          # Entità JPA
│   │       ├── dao/
│   │       │   └── TodoItemDAO.java       # Data Access Object
│   │       ├── servlet/
│   │       │   ├── HelloServlet.java      # Servlet di esempio
│   │       │   └── TodoServlet.java       # Servlet per gestione Todo
│   │       └── util/
│   │           └── HibernateUtil.java     # Utilità per Hibernate
│   ├── resources/
│   │   ├── hibernate.cfg.xml              # Configurazione Hibernate
│   │   └── database.sql                   # Script SQL
│   └── webapp/
│       ├── index.jsp                      # Pagina principale
│       └── WEB-INF/
│           └── web.xml                    # Configurazione web
```

## Dipendenze

- **Hibernate Core**: ORM per Java
- **MySQL Connector**: Driver per MySQL
- **JPA API**: Specifiche JPA
- **Servlet API**: Per le servlet web

## Setup del Database

1. **Installa MySQL** (se non già installato)
2. **Crea il database**:
   ```sql
   mysql -u root -p
   CREATE DATABASE todoapp;
   ```
3. **Configura le credenziali** in `src/main/resources/hibernate.cfg.xml`:
   ```xml
   <property name="hibernate.connection.username">root</property>
   <property name="hibernate.connection.password">TUA_PASSWORD</property>
   ```

## Come Testare

### 1. Compilazione
```bash
mvn clean compile
```

### 2. Packaging
```bash
mvn package
```

### 3. Test con Jetty (aggiungere plugin Jetty al pom.xml)
```bash
mvn jetty:run
```

### 4. Accesso all'applicazione
- **Home**: http://localhost:8080/webapptest
- **Todo List**: http://localhost:8080/webapptest/todos
- **Hello Servlet**: http://localhost:8080/webapptest/hello

## Funzionalità

### TodoServlet (`/todos`)
- **GET**: Visualizza tutti i todo con form per aggiungerne di nuovi
- **POST**: 
  - `action=add`: Aggiunge nuovo todo
  - `action=complete`: Segna todo come completato
  - `action=delete`: Elimina todo

### Caratteristiche
- ✅ Persistenza dati con Hibernate
- ✅ CRUD completo per Todo Items
- ✅ Interface web responsive
- ✅ Gestione errori base
- ✅ Escape HTML per sicurezza

## Configurazione Database

Il file `hibernate.cfg.xml` è configurato per:
- **Database**: MySQL 8.0+
- **Host**: localhost:3306
- **Database**: todoapp
- **Modalità**: `hbm2ddl.auto=update` (crea/aggiorna automaticamente le tabelle)

## Troubleshooting

### Errori di connessione al database
1. Verifica che MySQL sia in esecuzione
2. Controlla username/password in `hibernate.cfg.xml`
3. Assicurati che il database `todoapp` esista

### Errori di compilazione
1. Verifica che tutte le dipendenze siano nel `pom.xml`
2. Esegui `mvn clean compile`

### Errori Hibernate
1. Controlla i log nella console
2. Verifica la configurazione in `hibernate.cfg.xml`
3. Assicurati che la classe `TodoItem` sia mappata correttamente

## Estensioni Future

- Autenticazione utenti
- API REST
- Paginazione
- Ricerca e filtri
- Test unitari
- Deploy su server application