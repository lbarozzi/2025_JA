-- Script per creare il database e le tabelle per l'applicazione Todo

-- Crea il database
CREATE DATABASE IF NOT EXISTS todoapp;

-- Usa il database
USE todoapp;

-- Crea la tabella todo_items
-- Nota: Hibernate creerà automaticamente la tabella con hbm2ddl.auto=update
-- ma questo script può essere utile per setup manuali

CREATE TABLE IF NOT EXISTS todo_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

-- Inserisci alcuni dati di esempio
INSERT INTO todo_items (title, description, completed) VALUES
('Studiare Java', 'Completare il corso di Java Spring Boot', false),
('Configurare Hibernate', 'Impostare Hibernate con MySQL', true),
('Creare API REST', 'Sviluppare API REST per la gestione todo', false);