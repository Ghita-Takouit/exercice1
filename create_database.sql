-- Script SQL pour créer la base de données exercice1_db
-- Exécutez ce script dans MySQL Workbench ou votre client MySQL

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS exercice1_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Utiliser la base de données
USE exercice1_db;

-- Les tables seront créées automatiquement par Hibernate
-- lors de la première exécution du projet

-- Vérifier que la base de données a été créée
SHOW DATABASES LIKE 'exercice1_db';

-- Afficher un message de confirmation
SELECT 'Base de données exercice1_db créée avec succès !' AS Message;

