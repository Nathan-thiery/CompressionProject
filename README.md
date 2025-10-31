# Prérequis
- Java JDK 8 ou supérieur

# Structure du projet
```
.
├── src/
│   ├── Main.java
│   ├── Pkg_Compression/
│   │   └── *.java
│   ├── Pkg_Logger/
│   │   └── *.java
│   └── Pkg_MathsForCompression/
│       └── *.java
└── bin/
```

# Compilation

## Compiler tous les fichiers
```bash
javac -d bin src/Main.java src/Pkg_Compression/*.java src/Pkg_Logger/*.java src/Pkg_MathsForCompression/*.java
```

# Exécution

## Exécution directe sans arguments
```bash
java -cp bin Main
```

## Exécution avec arguments
```bash
java -cp bin Main arg1 arg2 arg3
```

### Exemples d'utilisation avec arguments
```bash
# Exemple : Créer un tableau de 5000 entiers générés aléatoirement par la méthode 4, et utiliser la méthode de compression/décompression 3.
java -cp bin Main 4 5000 3
```

# Nettoyage
```bash
rm -rf bin/
```