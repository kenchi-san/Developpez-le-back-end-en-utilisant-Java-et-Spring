# README - Lancer le projet Angular et Spring Boot

## Prérequis techniques

### Environnement global
- **Node.js** : Version 18 ou supérieure (assurez-vous d'avoir `npm` inclus avec Node.js).
- **Java** : Version 17 ou supérieure.
- **Gradle** : Version 7.6 ou supérieure (pour gérer les dépendances du projet Spring Boot).
- **MySQL** : Version 8 ou supérieure (pour la base de données).

### Projets individuels
#### Angular (Frontend)
- Angular CLI : Version 15 ou supérieure.
    - Installation (si besoin) :
      ```bash
      npm install -g @angular/cli
      ```

#### Spring Boot (Backend)
- JDK : Version 17.
- Gradle : Installation requise.

---

## Configuration des ports

- **Mokoon (Angular)** : Par défaut, l'application Angular utilise le port `3001`.
- **Spring Boot** : L'application backend utilise le port `8090`.

---

## Installation et lancement des projets

### 1. Installation et lancement du projet Angular

#### Installation des dépendances
Dans le répertoire du projet Angular :
```bash
npm install
```

#### Lancer le projet Angular
Pour lancer le projet Mokoon :
```bash
ng serve --port 3001
```

Une fois lancé, accédez à l'application Angular à l'adresse suivante :
```
http://localhost:4200
```

---

### 2. Installation et lancement du projet Spring Boot

#### Configuration de la base de données
1. Créez une base de données MySQL. Exemple : `mokoon_db`.
2. Mettez à jour le fichier `application.properties` avec vos identifiants :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mokoon_db
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_PASSWORD
```

#### Installation des dépendances
Dans le répertoire du projet Spring Boot :
```bash
gradle build
```

#### Lancer l'application Spring Boot
Pour lancer le backend :
```bash
gradle bootRun
```

Une fois lancé, accédez à Swagger (documentation de l'API) à l'adresse suivante :
```
http://localhost:3001/swagger-ui.html
```

---

## Détails sur le backend Spring Boot

L'application Spring Boot est configurée avec les composants suivants :

### Frameworks principaux
- **Spring Boot Starter Data JPA** : Gestion des entités et interaction avec la base de données via Hibernate.
- **Spring Boot Starter Validation** : Validation des entrées via des annotations Jakarta Validation.
- **Spring Boot Starter Web** : API REST et gestion des contrôleurs.
- **Spring Boot Starter Security** : Gestion de la sécurité (authentification et autorisation).

### Fonctionnalités additionnelles
- **JWT** (JSON Web Token) : Utilisé pour l'authentification et la sécurité avec `io.jsonwebtoken`.
- **Swagger** : Documentation de l'API avec `springdoc-openapi-starter-webmvc-ui`.

### Dépendances principales
Voici les dépendances principales du projet Spring Boot :
```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-web'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'com.mysql:mysql-connector-j'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
implementation 'jakarta.validation:jakarta.validation-api:3.0.2'
implementation 'org.hibernate.validator:hibernate-validator:8.0.0.Final'
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
```

---

## Commandes récapitulatives

### Lancer Angular
```bash
ng serve --port 3001
```

### Lancer Spring Boot
```bash
./gradlew bootRun  
```

### Accès à Swagger
```
http://localhost:3001/api/swagger-ui/index.html
```

