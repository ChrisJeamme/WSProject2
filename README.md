# WSProject2

## Description

Plateforme de gestion de photos de classe.

## Entités

Les **photographes** s'inscrivent directement sur le site.
Ils peuvent ajouter des **photos de classes** et des **photos individuelles**.
Les **directeurs d'écoles** s'inscrivent directement sur le site.
Une fois connectés, ils pourront **inscrire leurs écoles**.
Un directeur pourra donc avoir accès à une **interface de gestion** de son école, afin d'ajouter/modifier des **classes**, d'y ajouter des **élèves**.
C'est également sur cette interface qu'il pourra générer des **identifiants de connexion** pour les parents, qu'il pourra leur envoyer (ils pourront modifier leur mot de passe une fois connectés)
Les parents pourront grace à leur compte **visualiser** et **commander** les photos de classe et individuelles de leur enfant.
Tous les utilisateurs pourront **modifier leur mot de passe**.
Le directeur aura une interface de **visualisation des commandes**.

## Installation pour développement (Windows avec Eclipse, depuis Git)
- Importer le projet depuis Eclipse :  *File > Import > Git*
- Saisir l'url git: https://github.com/ChrisJeamme/WSProject2
- Importer en tant que General Project quand Eclipse le demande
- Convertir en projet Maven en faisant *Clic droit sur le projet > Configure > Convert in maven projet*
- Lancer un *maven install*
- Installation de la base de donnée: Installer la suite MySQL
- Lancer MySQL Workbench et ajouter un utilisateur *springuser* avec le mot de passe *ThePassword* avec tous les droits
- Ajouter un schéma vide nommé *wsprojet*
- (Retour dans Eclipse) Lancer en tant comme Spring Boot App
