# WSProject2

## Description

Plateforme de gestion de photos de classe.

## Rôles (types de compte)

* **Directeur** : 
Une fois son compte créé, il pourra inscrire son école, et ajouter des classes à cette dernière. Il est alors possible de créer et d'assigner des élèves à ces classes.
Lors de la création d'un élève les informations recueillies permettent la création du compte du **Parent** (login: email / pass: "parent"). Le **directeur** dispose d'une interface de visualisation des commandes.

* **Parent** : A partir de ce compte, ils pourront consulter et commander les photos de classe et individuelles de leur enfant.

* **Photographe** : Ils s'inscrivent séparément.
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
