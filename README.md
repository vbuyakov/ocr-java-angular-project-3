# ChâTop API

API REST pour la plateforme ChâTop - Application de location de biens immobiliers.

## 📚 Documentation

- **[Documentation API](./API_DOCUMENTATION.md)** - Documentation complète des endpoints de l'API
- **[Documentation Base de Données](./DATABASE_DOCUMENTATION.md)** - Structure et schéma de la base de données

## 🚀 Démarrage rapide

### Prérequis

- Java 21
- Maven 3.6+
- MySQL 8.0+
- Node.js (pour le frontend, si applicable)

### Installation

1. **Cloner le repository**
   ```bash
   git clone <repository-url>
   cd P3-Api
   ```

2. **Configurer les variables d'environnement**
   ```bash
   cp .env.example .env
   ```
   
   Puis éditer le fichier `.env` avec vos paramètres :
   - `SERVER_PORT` : Port du serveur
   - `UPLOAD_FOLDER` : Dossier de stockage des images
   - `DB_URL` : URL de connexion MySQL
   - `DB_USERNAME` : Nom d'utilisateur MySQL
   - `DB_PASSWORD` : Mot de passe MySQL
   - `JWT_SECRET_KEY` : Clé secrète JWT
   - `JWT_EXPIRATION_TIME` : Durée d'expiration des tokens

3. **Créer la base de données**
   ```bash
   mysql -u root -p < dump.sql
   ```
   
   Ou suivre les instructions dans la [Documentation Base de Données](./DATABASE_DOCUMENTATION.md)

4. **Créer le dossier de stockage des images**
   ```bash
   mkdir -p ./uploads/rentals
   chmod -R 755 ./uploads
   ```

5. **Compiler et lancer l'application**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

L'application sera accessible sur `http://localhost:${SERVER_PORT}`

## 📖 Documentation Swagger

Une fois l'application démarrée, la documentation Swagger est disponible à :
```
http://localhost:${SERVER_PORT}/swagger-ui/index.html
```

## 🏗️ Architecture

- **Framework** : Spring Boot 3.5.8
- **Base de données** : MySQL 8.0+
- **Sécurité** : Spring Security + JWT
- **Documentation API** : SpringDoc OpenAPI (Swagger)

## 📦 Technologies utilisées

- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- MapStruct
- Lombok
- MySQL Connector
- SpringDoc OpenAPI

Pour plus de détails, consultez la [Documentation API](./API_DOCUMENTATION.md).

## 🔐 Authentification

L'API utilise l'authentification JWT. Pour obtenir un token :

1. Créer un compte via `/api/auth/register`
2. Se connecter via `/api/auth/login` pour obtenir un token
3. Utiliser le token dans l'en-tête `Authorization: Bearer <token>`

## 📝 Endpoints principaux

- **Authentification** : `/api/auth/*`
- **Locations** : `/api/rentals/*`
- **Messages** : `/api/messages`
- **Utilisateurs** : `/api/user/*`

Pour la liste complète des endpoints, consultez la [Documentation API](./API_DOCUMENTATION.md).

## 🗄️ Base de données

La base de données contient trois tables principales :
- `users` : Utilisateurs de l'application
- `rentals` : Locations disponibles
- `messages` : Messages entre utilisateurs

Pour plus de détails sur la structure, consultez la [Documentation Base de Données](./DATABASE_DOCUMENTATION.md).

## 🧪 Tests

```bash
./mvnw test
```

## 📄 Licence

Ce projet fait partie d'un projet d'apprentissage.

## 👤 Auteur

Projet développé dans le cadre d'une formation.

