import Pkg_Compression.*;
import Pkg_Logger.LogLevel;
import Pkg_Logger.Writter;

import static Pkg_Compression.CompressionLevel.*;

public class Main {

    public static void main(String args[]){
        Writter.setCurrent_Log_Level(LogLevel.FINEST);

        // Cas où l'utilisateur entre ses propres entiers
        if(args.length > 0){

            int[] commandLineArgs = new int[args.length];
            for (int i = 0; i < args.length; i++){
                commandLineArgs[i] = Integer.parseInt(args[i]);
            }
            IntegerArray infoCommandLine = new IntegerArray(commandLineArgs);
            CompressionFactory.getCompression(FIRST).compress(infoCommandLine);


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
            CompressionFactory.getCompression(THIRD).decompress(info3);

        }

    }
}
