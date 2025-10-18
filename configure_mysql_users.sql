-- Script SQL pour créer un nouvel utilisateur avec tous les droits
-- Exécutez ce script dans phpMyAdmin en tant qu'administrateur

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS exercice1_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Option 1: Donner tous les droits à root@localhost
GRANT ALL PRIVILEGES ON exercice1_db.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- Option 2: Créer un nouvel utilisateur 'exercice_user' sans mot de passe
CREATE USER IF NOT EXISTS 'exercice_user'@'localhost';
GRANT ALL PRIVILEGES ON exercice1_db.* TO 'exercice_user'@'localhost';
FLUSH PRIVILEGES;

-- Option 3: Créer un nouvel utilisateur 'exercice_user' avec mot de passe
-- Décommentez les lignes suivantes et remplacez 'votre_mot_de_passe' par votre mot de passe
-- DROP USER IF EXISTS 'exercice_user'@'localhost';
-- CREATE USER 'exercice_user'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
-- GRANT ALL PRIVILEGES ON exercice1_db.* TO 'exercice_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Vérifier les utilisateurs et leurs privilèges
SELECT User, Host FROM mysql.user WHERE User IN ('root', 'exercice_user');

SELECT 'Configuration terminée! Base de données exercice1_db prête.' AS Message;

