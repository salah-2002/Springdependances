# Projet Injection de Dépendances – Part 1 & Part 2

Ce projet illustre la mise en œuvre et l’évolution d’un système d’injection de dépendances en Java.

## Partie 1 – Approches classiques & Spring

La première partie présente plusieurs méthodes pour injecter des dépendances entre composants métier et DAO :

* **Instanciation statique** avec `new`
* **Instanciation dynamique** via réflexion et fichier de configuration
* **Spring IoC (XML)** : définition des beans dans `applicationContext.xml`
* **Spring IoC (Annotations)** : utilisation de `@Component` et `@Autowired`

Chaque approche montre comment réduire le couplage fort et améliorer la flexibilité.

## Partie 2 – Mini Framework IoC

La deuxième partie développe un mini framework inspiré de Spring, capable de gérer l’injection de dépendances :

* **Version XML** : configuration via `beans.xml` et parsing avec JAXB (OXM)
* **Version annotations** : découverte automatique avec `@Component`, injection via `@Inject`
* Support des trois types d’injection : **constructeur**, **setter**, et **attribut**

Le framework charge les définitions, instancie les composants et relie automatiquement leurs dépendances.

## Exécution

Les classes `AppStatic`, `AppDynamic`, `XmlMain`, `AnnoMain` (Partie 1) et les mains XML/Annotations du framework (Partie 2) démontrent le fonctionnement.
Le projet est structuré pour être exécuté avec **Maven** ou le **Maven Wrapper** (`mvnw`).

