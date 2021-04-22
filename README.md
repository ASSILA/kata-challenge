Enoncé :

Nos magasins produisent tous les jours des fichiers de logs contenant les informations relatives à leur activité de vente journalière. De plus, chaque magasin possède son propre référentiel de prix journalier.

Le fichier des transactions journalières contient ces infos: txId|datetime|magasin|produit|qte
Et celui du référentiel produit: produit|prix

où :
txId : id de transaction (nombre)
datetime : date et heure au format ISO 8601
magasin : UUID identifiant le magasin produit :
id du produit (nombre)
qte : quantité (nombre)
prix : prix du produit en euros

Besoin :

Déterminer, pour chaque heure, les 100 produits qui ont les meilleures ventes et ceux qui génèrent le plus gros Chiffre d'Affaire par magasin.
