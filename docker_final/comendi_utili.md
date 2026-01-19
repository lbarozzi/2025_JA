# Build e avvio di tutti i servizi
docker-compose up -d --build

# Visualizza i log
docker-compose logs -f

# Visualizza lo stato dei servizi
docker-compose ps

# Stop di tutti i servizi
docker-compose down

# Stop e rimozione dei volumi (ATTENZIONE: cancella i dati!)
docker-compose down -v