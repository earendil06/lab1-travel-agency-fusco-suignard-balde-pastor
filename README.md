# SOA Lab 1 : Florent Pastor, Thomas Suignard, Cello Balde, Anthony Fusco

## Flight planner :
Paradigme Document

Interface :

Le chemin d’accès est /flight. L’utilisateur choisit ensuite la fonction en passant les paramètres dans un JsonObject.

La réponse est un JsonArray qui contient tous les vols qui satisfont la requête.

Design Choice :

Ce service utilise le paradigme Document pour son implémentation.

Nous avons choisi ce paradigme car les paramètres de la requête sont complexes et peuvent varier selon la requête et nous voulons manipuler un objet utilisateur qui définit ces paramètres. 


# Hotel Planner :
Paradigme RPC

Interface :

   Envoie d’une méthode getHotelsForTravels avec pour arguments la ville de destination et la date du voyage.
   
   La réponse est une enveloppe SOAP qui contient un JSONArray d'hôtels disponibles associés à un id unique.

Design Choice :

   Ce service possède une interface stricte et ne demande qu’un seul appel de méthode.
   
   RPC permet de renforcer ces propriétés, on ne rajoute pas de complexité à ce service et on s’assure de bien encapsuler les paramètres dont on a besoin pour la requète.

## Car Planner :
Paradigme RPC

Interface :

   Envoie d’une méthode getCarByPlace avec pour arguments la ville de destination et la durée de la location en heure.
   
   La réponse est une enveloppe SOAP qui contient un JSONArray de voitures de location disponibles pour un temps au minimum le temps spécifié dans la requête associé à un id unique.

Design Choice :

   Comme pour le Car planner : Ce service possède une interface stricte et ne demande qu’un seul appel de méthode.
   
   RPC permet de renforcer ces propriétés, on ne rajoute pas de complexité à ce service et on s’assure de bien encapsuler les paramètres dont on as besoin pour la requète.

## Travel Planner :

Paradigme REST

Interface :

   Séparé en deux parties, /TravelPlannerService pour la gestion de nouvelles requêtes de voyage et /TravelAcceptationService pour valider/refuser les requêtes existantes.




- /TravelPlannerService

    - Créer un nouvelle requête
        - Méthode POST sur .../service-travel-manager/TravelPlannerService/request
            Avec en Body un JSONObject qui contient l’email de l’employé, une liste d’ID des hôtels et une liste d’ID des vols demandés
Le service renvoie l’ID unique de la requête
    - Demander une requête existante
        - Méthode GET sur .../service-travel-manager/TravelPlannerService/request retourne les uid de toutes les requêtes existante
        - Méthode GET sur .../service-travel-manager/TravelPlannerService/request/email/{email} retourne les uid de toutes les requêtes existante pour un certain email
        - Méthode GET sur .../service-travel-manager/TravelPlannerService/request/uid/{uid} retourne une requête avec un uid précis. Retourne une erreur NOT FOUND si l’uid n’est pas présent
- /TravelAcceptationService
    - Valider/Refuser une requête
        - Méthode POST sur .../service-travel-manager/TravelAcceptationService/validatedRequest/uid/{uid} deplace la requête dans les ressources validé et “envoi un mail” à l’employé concerné
        - Méthode POST sur .../service-travel-manager/TravelAcceptationService/refusedRequest/uid/{uid} deplace la requête dans les ressources refusé et “envoi un mail” à l’employé concerné
    - Voir les requêtes refusées/validées
        - De facon similaire aux autres méthodex GET avec les urls .../service-travel-manager/TravelAcceptationService/refusedRequest/uid/{uid}, .../service-travel-manager/TravelAcceptationService/validatedRequest/uid/{uid}, …

Design Choice :

   En voyant les requêtes en attente, les requêtes validées et les requêtes refusées comme des ressources, on peut alors construire un service REST.
   
Les utilisateurs manipulent ces ressources plus facilement grâce aux conventions des verbes HTTP, en effet un appelle GET sur l’un des trois URI de ressources renverra des URI vers les ressources actuelle de façon idempotent,
 
un POST d’une nouvelle requête ou d’une validation/refus de requête modifiera la base de ressources.

La validation/refus d’une requête entraîne son déplacement de la base de requêtes en attente à la base de requêtes validés ou refusés, cette opération entraîne donc un effet de bord.

Hors l’utilisation des méthodes POST comme défini par l’architecture REST n’est considérée ni sécurisé, ni idempotent.

L’utilisateur de l’API s’attend donc à un comportement de ce genre.

Une requête REST doit être stateless. Pour cela, l'état d’une requête qui peux être en attente/validé/refusé est abstrait par sa position dans les dossiers de ressources.
