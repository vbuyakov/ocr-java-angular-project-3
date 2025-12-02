# Définition d'API

## Configuration initiale

### Variables d'environnement

Avant de démarrer l'application, il est nécessaire de configurer les variables d'environnement :

1. **Copier le fichier `.env.example` vers `.env`**
   ```bash
   cp .env.example .env
   ```

2. **Configurer les variables d'environnement dans le fichier `.env`** :
   - `SERVER_PORT` : Port du serveur (ex: 3001)
   - `UPLOAD_FOLDER` : Chemin vers le dossier de stockage des images (ex: ./uploads)
   - `DB_URL` : URL de connexion à la base de données MySQL (ex: jdbc:mysql://localhost:3306/chatop_db)
   - `DB_USERNAME` : Nom d'utilisateur de la base de données
   - `DB_PASSWORD` : Mot de passe de la base de données
   - `JWT_SECRET_KEY` : Clé secrète pour la génération des tokens JWT
   - `JWT_EXPIRATION_TIME` : Durée d'expiration des tokens JWT (ex: 86400000 pour 24h en millisecondes)

### Configuration du dossier de stockage des images

L'application nécessite un dossier pour stocker les images uploadées. Suivez ces étapes :

1. **Créer le dossier principal** selon la valeur de `UPLOAD_FOLDER` dans votre fichier `.env`
   ```bash
   mkdir -p ${UPLOAD_FOLDER}
   ```

2. **Créer le sous-dossier pour les locations**
   ```bash
   mkdir -p ${UPLOAD_FOLDER}/rentals
   ```

3. **Définir les permissions en lecture et écriture**
   ```bash
   chmod -R 755 ${UPLOAD_FOLDER}
   ```
   
   Ou pour donner les permissions complètes (lecture, écriture, exécution) :
   ```bash
   chmod -R 777 ${UPLOAD_FOLDER}
   ```

**Exemple :** Si `UPLOAD_FOLDER=./uploads`, les commandes seraient :
```bash
mkdir -p ./uploads
mkdir -p ./uploads/rentals
chmod -R 755 ./uploads
```

---
# Endpoints API

## Authentification

| URL | Méthode | Description | Params (path/query) | Body (JSON) | Status | Exemple de réponse |
| --- | --- | --- | --- | --- | --- | --- |
| `/api/auth/register` | POST | Créer un compte utilisateur | - | `{ "name": "string", "email": "string", "password": "string" }` | 201, 400, 409, 500 | `201 Created` (no body) |
| `/api/auth/login` | POST | Authentifier un utilisateur | - | `{ "email": "string", "password": "string" }` | 200, 400, 401, 500 | `{ "token": "jwt" }` |
| `/api/auth/me` | GET | Récupérer les infos de l'utilisateur actuellement authentifié (JWT) | - | - | 200, 401, 500 | `{ "id": 1, "name": "Test TEST", "email": "test@test.com", "created_at": "2022/02/02", "updated_at": "2022/08/02" }` |

---

## Rentals

| URL | Méthode | Description | Params (path/query) | Body (JSON) | Status | Exemple de réponse |
| --- | --- | --- | --- | --- | --- | --- |
| `/api/rentals` | GET | Récupérer la liste de toutes les locations | - | - | 200, 401, 500 | `{ "rentals": [ { "id": 1, "name": "test house 1", "surface": 50.0, "price": 300.0, "picture": "image.jpg", "description": "Description", "owner_id": "1", "created_at": "2012/12/02", "updated_at": "2014/12/02" } ] }` |
| `/api/rentals/{id}` | GET | Récupérer le détail d'une location par son id | Params (path) = `:id` (location) | - | 200, 401, 404, 500 | `{ "id": 1, "name": "dream house", "surface": 24.0, "price": 30.0, "picture": "image.jpg", "description": "Description", "owner_id": "1", "created_at": "2012/12/02", "updated_at": "2014/12/02" }` |
| `/api/rentals` | POST | Créer une nouvelle location | - | `{ "name": "string", "surface": number, "price": number, "picture": File, "description": "string" }` (multipart/form-data) | 201, 401, 400, 500 | `{ "message": "Rental created !" }` |
| `/api/rentals/{id}` | PUT | Mettre à jour une location existante | Params (path) = `:id` (location) | `{ "name": "string", "surface": number, "price": number, "picture": File (optional), "description": "string" }` (multipart/form-data) | 200, 401, 404, 400, 500 | `{ "message": "Rental updated !" }` |

---

## Messages

| URL | Méthode | Description | Params (path/query) | Body (JSON) | Status | Exemple de réponse |
| --- | --- | --- | --- | --- | --- | --- |
| `/api/messages` | POST | Envoyer un message | - | `{ "rental_id": number, "user_id": number, "message": "string" }` | 201, 400, 401, 500 | `{ "message": "Message sent successfully!" }` |

---

## User Information

| URL | Méthode | Description | Params (path/query) | Body (JSON) | Status | Exemple de réponse |
| --- | --- | --- | --- | --- | --- | --- |
| `/api/user/{id}` | GET | Récupérer le profil public d'un utilisateur (propriétaire) | Params (path) = `:id` (utilisateur) | - | 200, 401, 404, 500 | `{ "id": 2, "name": "Owner Name", "email": "test@test.com", "created_at": "2022/02/02", "updated_at": "2022/08/02" }` |

---

# Fonctionnalités majeures

- **Inscription, connexion, déconnexion**
- **Page d'accueil** avec les offres, un bouton pour la création, un bouton pour l'édition et un bouton pour le détail des offres. Un lien vers la page : Les infos utilisateurs.

**Boutons :**
- Le **détail** permet de transmettre un message au propriétaire et recevoir un message message envoyé.
- L'**édition** permet de modifier une offre, le save retourne message : rental update.
- La **création** permet de créer une offre, le save retourne un message rental created.

## Les DTO côté back

- **RegisterUserDto** (pour `/api/auth/register`)
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
  ```

- **LoginUserDto** (pour `/api/auth/login`)
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```

- **LoginResponse** (pour `/api/auth/login`)
  ```json
  {
    "token": "jwt"
  }
  ```

- **UserInfoResponse** (pour `/api/auth/me` ou `/api/user/{id}`)
  ```json
  {
    "id": number,
    "name": "string",
    "email": "string",
    "created_at": "string",
    "updated_at": "string"
  }
  ```

- **RentalDto** (POST/PUT rentals - multipart/form-data)
  ```json
  {
    "name": "string",
    "surface": number,
    "price": number,
    "picture": File,
    "description": "string"
  }
  ```

- **RentalResponse** (GET rentals)
  ```json
  {
    "id": number,
    "name": "string",
    "surface": number,
    "price": number,
    "picture": "string",
    "description": "string",
    "owner_id": "string",
    "created_at": "string",
    "updated_at": "string"
  }
  ```

- **RentalsResponse** (GET `/api/rentals`)
  ```json
  {
    "rentals": [ RentalResponse ]
  }
  ```

- **MessageDto** (pour `/api/messages`)
  ```json
  {
    "rental_id": number,
    "user_id": number,
    "message": "string"
  }
  ```

- **ResponseMessage** (pour les réponses de succès)
  ```json
  {
    "message": "string"
  }
  ```

---

# Dépendances Spring Boot typiques pour ce projet

- **spring-boot-starter-web**
  Pour exposer les controllers REST, gérer les routes HTTP, la sérialisation JSON ... pour l'API.

- **spring-boot-starter-data-jpa**
  Pour la couche d'accès aux données avec JPA/Hibernate et les Repository, pour mapper les entités vers MySQL.

- **spring-boot-starter-validation**
  Pour valider les DTO : emails, champs obligatoires, tailles, etc, par exemple : @NotBlank, @Email, @Size.

- **spring-boot-starter-security**
  Pour gérer la sécurité : filtres, config des routes protégées, gestion du contexte d'authentification, etc., permet de construire le filtre JWT dessus.

- **jjwt** (lib JWT - jjwt-api, jjwt-impl, jjwt-jackson)
  Pour créer et vérifier les tokens JWT (login / auth).

- **mysql-connector-j**
  Driver JDBC pour communiquer avec la base MySQL.

- **lombok**
  Pour réduire le code boilerplate : @Getter, @Setter, @Builder, etc.

- **mapstruct** et **mapstruct-processor**
  Pour le mapping automatique entre entités et DTOs, génération de code à la compilation.

- **springdoc-openapi-starter-webmvc-ui**
  Pour générer une doc Swagger / OpenAPI de l'API et l'exposer via une UI web.

- **jackson-datatype-jsr310**
  Pour la sérialisation/désérialisation des types Java 8+ (LocalDateTime, etc.) en JSON.

- **dotenv-java**
  Pour charger les variables d'environnement depuis un fichier .env.

