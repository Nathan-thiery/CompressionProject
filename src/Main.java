import Pkg_Compression.*;
import Pkg_Logger.LogLevel;
import Pkg_Logger.Writter;
import java.util.Random;

import static Pkg_Compression.CompressionLevel.*;

public class Main {

    public static void main(String args[]){
        Writter.setCurrent_Log_Level(LogLevel.INFO);

        // Cas où l'utilisateur entre ses propres entiers
        if(args.length > 0){

            if (args.length != 3) {
                Writter.config_log("Usage : java Main <mode : integer> <size : integer> <compression_mode : integer>");
                Writter.config_log("\n\tmode [1] :\n\t\t> Generate random integers between 0 and 100 in a table of length 'size'.");
                Writter.config_log("\n\tmode [2] :\n\t\t> Generate random integers between 100 000 and 10 000 000 in a table of length 'size'.");
                Writter.config_log("\n\tmode [3] :\n\t\t> Generate a table of integers of length 'size' with a : \n\t\t\t95% probability of a random integer between 0 and 100\n\t\t\t5% probability of a random integer between 100 000 and 10 000 000.");
                Writter.config_log("\n\tmode [4] :\n\t\t> Generate a table of integers of length 'size' with a : \n\t\t\t5% probability of a random integer between 0 and 100\n\t\t\t95% probability of a random integer between 100 000 and 10 000 000.");
                Writter.config_log("\n\tmode [5] :\n\t\t> Generate random integers between 0 and 10 000 000 in a table of length 'size'.");
                Writter.config_log("\n\tmode [6] :\n\t\t> Generate a table of integers of length 'size' which compute a random probability to generate integers between 0 and 100 and the rest of the probability to generate integers between 100 000 and 10 000 000.");
                Writter.config_log("In any cases, compresses it with the compression mode number 'compression_mode'");
                Writter.config_log("\nValues : \n\tmode [1;6]\n\tsize[>=1]\n\tcompression_mode[1;3]");
                return;
            }

            int mode = -1;
            //int size = -1;
            int compression_mode = -1;
            Random rand = new Random();
            try {
                mode = Integer.parseInt(args[0]);
                //size = Integer.parseInt(args[1]);
                compression_mode = Integer.parseInt(args[2]);

            } catch (NumberFormatException e) {
                Writter.warning_log("Arguments must be integers.");
                System.exit(1);
            }

            // TODO : Placer une boucle incrémentale sur la size du tableau
            // Valeurs = 10 - 50 - 100 - 250 - 500 - 800 - 1 000
            int[] sizes = {10, 50, 100, 250, 500, 800, 1000, 5000, 10000};
            for (int size : sizes) {
                for (int repetitions = 0; repetitions <10 ; repetitions++){

                    // Création du tableau selon le mode choisi
                    int[] tableau = creerTableau(mode, size);

                    switch (compression_mode) {
                        case 1:
                            long start = System.nanoTime();
                            IntegerArray RandomIntsData1 = new IntegerArray(tableau);
                            CompressionFactory.getCompression(FIRST).compress(RandomIntsData1);
                            //CompressionFactory.getCompression(FIRST).get(rand.nextInt(size), RandomIntsData1);
                            CompressionFactory.getCompression(FIRST).decompress(RandomIntsData1);
                            long end = System.nanoTime();
                            long timer = (end - start);
                            Writter.file_log(compression_mode + " " + mode + " " + size + " " + (timer / 1000000.0));
                            break;
                        case 2:
                            long start2 = System.nanoTime();
                            IntegerArray RandomIntsData2 = new IntegerArray(tableau);
                            CompressionFactory.getCompression(SECOND).compress(RandomIntsData2);
                            //CompressionFactory.getCompression(SECOND).get(rand.nextInt(size), RandomIntsData2);
                            CompressionFactory.getCompression(SECOND).decompress(RandomIntsData2);
                            long end2 = System.nanoTime();
                            long timer2 = (end2 - start2);
                            Writter.file_log(compression_mode + " " + mode + " " + size + " " + (timer2 / 1000000.0));
                            break;
                        case 3:
                            long start3 = System.nanoTime();
                            IntegerArray RandomIntsData3 = new IntegerArray(tableau);
                            CompressionFactory.getCompression(THIRD).compress(RandomIntsData3);
                            //CompressionFactory.getCompression(THIRD).get(rand.nextInt(size), RandomIntsData3);
                            CompressionFactory.getCompression(THIRD).decompress(RandomIntsData3);
                            long end3 = System.nanoTime();
                            long timer3 = (end3 - start3);
                            Writter.file_log(compression_mode + " " + mode + " " + size + " " + (timer3 / 1000000.0));
                            break;
                        default:
                            Writter.warning_log("Value given for arg3 is wrong.\nExpected value between 1 and 3.\n");
                            System.exit(1);
                    }
                }

            } // Fin de la boucle avec les différentes Sizes

        // Cas où l'utilisateur appelle le programme sans arguments
        }else{
            // En utilisant le CompressionFactory :

            // Méthode 1
            IntegerArray info3 = new IntegerArray(new int[]{123, 333, 432, 11123});
            CompressionFactory.getCompression(FIRST).compress(info3);
            CompressionFactory.getCompression(FIRST).get(3, info3);
            CompressionFactory.getCompression(FIRST).decompress(info3);

            // Méthode 2
            CompressionFactory.getCompression(SECOND).compress(info3);
            CompressionFactory.getCompression(SECOND).get(3, info3);
            CompressionFactory.getCompression(SECOND).decompress(info3);

            // Méthode 3
            CompressionFactory.getCompression(THIRD).compress(info3);
            CompressionFactory.getCompression(THIRD).get(0, info3);
            CompressionFactory.getCompression(THIRD).decompress(info3);

        }

    }

    public static int[] creerTableau(int mode, int taille) {
        int[] tableau = new int[taille];
        Random rand = new Random();

        switch (mode) {
            case 1:
                // Valeurs entre 0 et 100
                for (int i = 0; i < taille; i++) {
                    tableau[i] = rand.nextInt(101);
                }
                break;

            case 2:
                // Valeurs entre 100_000 et 10_000_000
                for (int i = 0; i < taille; i++) {
                    tableau[i] = 100_000 + rand.nextInt(9_900_001);
                }
                break;

            case 3:
                // 95% entre 0-100, 5% entre 100_000-10_000_000
                for (int i = 0; i < taille; i++) {
                    if (rand.nextDouble() < 0.95)
                        tableau[i] = rand.nextInt(101);
                    else
                        tableau[i] = 100_000 + rand.nextInt(9_900_001);
                }
                break;

            case 4:
                // 5% entre 0-100, 95% entre 100_000-10_000_000
                for (int i = 0; i < taille; i++) {
                    if (rand.nextDouble() < 0.05)
                        tableau[i] = rand.nextInt(101);
                    else
                        tableau[i] = 100_000 + rand.nextInt(9_900_001);
                }
                break;

            case 5:
                // Valeurs entre 0 et 10_000_000
                for (int i = 0; i < taille; i++) {
                    tableau[i] = rand.nextInt(10_000_001);
                }
                break;

            case 6:
                // % aléatoire de chance pour 0-100 vs 100_000-10_000_000
                double probaPetiteValeur = rand.nextDouble();
                System.out.printf("Probabilité pour valeurs entre 0 et 100 : %.2f%%%n", probaPetiteValeur * 100);
                for (int i = 0; i < taille; i++) {
                    if (rand.nextDouble() < probaPetiteValeur)
                        tableau[i] = rand.nextInt(101);
                    else
                        tableau[i] = 100_000 + rand.nextInt(9_900_001);
                }
                break;

            default:
                System.out.println("Mode invalide. Choisissez un mode entre 1 et 6.");
                System.exit(1);
        }

        return tableau;
    }

}
