# WSProject2

## Description

Plateforme de gestion de photos de classe.

## Entités

Un directeur peut inscrire son école.
Il aura alors un compte et pourra gérer son école, ajouter les classes ainsi que les élèves.
Il pourra alors générer des identifiants de connexion qu'il pourra communiquer aux parents.
Les parents pourront grace à leur compte commander les photos de classe et individuelles de leur enfant.
Le directeur aura une interface de visualisation des commandes.
Les photographes eux, s'inscrivent séparément.
Ils peuvent ajouter des photos de classes et des photos individuelles.

## Installation pour développement (Windows avec Eclipse, depuis Git)
- Importer le projet depuis Eclipse :  *File > Import > Git*
- Importer en tant que General Project quand Eclipse le demande
- Convertir en projet Maven en faisant *Clic droit sur le projet > Configure > Convert in maven projet*
- Lancer un maven install
- Installation de la base de donnée: Installer la suite MySQL
- Lancer MySQL Workbench et ajouter un utilisateur *springuser* avec le mot de passe *ThePassword*
- Ajouter un schéma vide nommé *wsprojet*
- (Retour dans Eclipse) Lancer en tant comme Spring Boot App
