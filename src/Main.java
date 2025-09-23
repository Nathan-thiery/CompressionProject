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
            IntegerArray info1 = new IntegerArray(new int[]{1, 2, 3, 4, 5});
            FirstCompression compress1 = new FirstCompression();
            compress1.compress(info1);
            compress1.decompress(info1);

            IntegerArray info2 = new IntegerArray(new int[]{15, 24, 33, 2});
            compress1.compress(info2);
            compress1.get(2, info2);

            // En utilisant le CompressionFactory :
            IntegerArray info3 = new IntegerArray(new int[]{123, 333, 432, 11123});
            CompressionFactory.getCompression(FIRST).compress(info3);
            CompressionFactory.getCompression(FIRST).get(3, info3);
            CompressionFactory.getCompression(FIRST).decompress(info3);

            CompressionFactory.getCompression(SECOND).compress(info3);
            CompressionFactory.getCompression(SECOND).get(3, info3);
            CompressionFactory.getCompression(SECOND).decompress(info3);

        }

    }
}
