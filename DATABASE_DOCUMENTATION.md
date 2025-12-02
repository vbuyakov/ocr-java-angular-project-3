# Les entités métiers à manipuler

> **Note :** Avant d'utiliser la base de données, assurez-vous de configurer les paramètres de connexion dans votre fichier `.env` :
> - `DB_URL` : URL de connexion à la base de données MySQL (ex: `jdbc:mysql://localhost:3306/chatop_db`)
> - `DB_USERNAME` : Nom d'utilisateur de la base de données
> - `DB_PASSWORD` : Mot de passe de la base de données
>
> Ces variables sont utilisées par l'application Spring Boot pour se connecter à la base de données.

---

## Table `users`

```sql
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Description :**
- `id` : Identifiant unique de l'utilisateur (clé primaire, auto-incrémenté)
- `email` : Adresse email de l'utilisateur (unique, nullable)
- `name` : Nom de l'utilisateur (obligatoire)
- `password` : Mot de passe hashé de l'utilisateur (obligatoire)
- `created_at` : Date de création (nullable)
- `updated_at` : Date de mise à jour (nullable)

---

## Table `rentals`

```sql
CREATE TABLE `rentals` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surface` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rentals_owner` (`owner_id`),
  CONSTRAINT `fk_rentals_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Description :**
- `id` : Identifiant unique de la location (clé primaire, auto-incrémenté)
- `name` : Nom de la location (obligatoire)
- `surface` : Surface en m² (nullable, type double)
- `price` : Prix de la location (nullable, type double)
- `picture` : Chemin vers l'image de la location (nullable)
- `description` : Description de la location (nullable, max 2000 caractères)
- `owner_id` : Identifiant du propriétaire (clé étrangère vers `users.id`, nullable)
- `created_at` : Date de création (nullable)
- `updated_at` : Date de mise à jour (nullable)

---

## Table `messages`

```sql
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(2000) NOT NULL,
  `rental_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_messages_rental` (`rental_id`),
  KEY `fk_messages_user` (`user_id`),
  CONSTRAINT `fk_messages_rental` FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`),
  CONSTRAINT `fk_messages_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Description :**
- `id` : Identifiant unique du message (clé primaire, auto-incrémenté)
- `message` : Contenu du message (obligatoire, max 2000 caractères)
- `rental_id` : Identifiant de la location concernée (clé étrangère vers `rentals.id`, nullable)
- `user_id` : Identifiant de l'utilisateur expéditeur (clé étrangère vers `users.id`, nullable)
- `created_at` : Date de création (obligatoire)
- `updated_at` : Date de mise à jour (nullable)

---

## Contraintes des clés étrangères

```sql
-- Contrainte pour rentals.owner_id
ALTER TABLE `rentals`
ADD CONSTRAINT `fk_rentals_owner`
FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

-- Contraintes pour messages
ALTER TABLE `messages`
ADD CONSTRAINT `fk_messages_rental`
FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`);

ALTER TABLE `messages`
ADD CONSTRAINT `fk_messages_user`
FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
```

---

# Script de création de la base de données

```sql
-- Création de la base de données ChâTop
CREATE DATABASE IF NOT EXISTS chatop_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE chatop_db;

-- Table USERS
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table RENTALS
CREATE TABLE IF NOT EXISTS `rentals` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surface` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rentals_owner` (`owner_id`),
  CONSTRAINT `fk_rentals_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table MESSAGES
CREATE TABLE IF NOT EXISTS `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(2000) NOT NULL,
  `rental_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_messages_rental` (`rental_id`),
  KEY `fk_messages_user` (`user_id`),
  CONSTRAINT `fk_messages_rental` FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`),
  CONSTRAINT `fk_messages_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## Utilisateur SQL dédié

```sql
-- Création d'un utilisateur dédié (éviter le compte root)
CREATE USER IF NOT EXISTS 'chatop_user'@'%' IDENTIFIED BY 'mot_de_passe_solide';

-- Octroiement des privilèges sur la base chatop_db
GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'%';

-- Forcer MySQL à relire ces tables pour prendre en compte les nouveaux droits sans redémarrer le serveur
FLUSH PRIVILEGES;
```

---

## Vérifier les tables

```sql
-- Afficher la liste de toutes les bases de données disponibles sur le serveur MySQL
SHOW DATABASES;

-- Sélectionner la base de données du projet ChâTop
USE chatop_db;

-- Afficher la liste des tables présentes dans la base de données chatop_db
SHOW TABLES;

-- Vérifier le contenu de la table des utilisateurs (users)
SELECT * FROM users;

-- Vérifier le contenu de la table des locations (rentals)
SELECT * FROM rentals;

-- Vérifier le contenu de la table des messages (messages)
SELECT * FROM messages;
```

