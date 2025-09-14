#!/bin/bash

SRC_DIR=src
BIN_DIR=temps

# Création du dossier bin s'il n'existe pas
mkdir -p $BIN_DIR

# Compilation de tous les fichiers Java du dossier src vers bin
javac -d $BIN_DIR $SRC_DIR/*.java

# Vérification que la compilation a réussi
if [ $? -eq 0 ]; then
    echo "Compilation réussie, lancement du programme..."
    # Exécution : on précise le chemin des .class avec -cp (classpath)
    java -cp $BIN_DIR Main 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15

    # Nettoyage : suppression des .class
    rm -rf $BIN_DIR
    echo "Nettoyage terminé : dossier bin supprimé."
else
    echo "Erreur de compilation."
fi