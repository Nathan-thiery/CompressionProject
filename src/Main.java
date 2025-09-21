
public class Main {

    public static void main(String args[]){
        Writter.setCurrent_Log_Level(LogLevel.FINEST);

        // Cas où l'utilisateur entre ses propres entiers
        if(args.length > 0){

            int[] commandLineArgs = new int[args.length];
            for (int i = 0; i < args.length; i++){
                commandLineArgs[i] = Integer.parseInt(args[i]);
            }
            // TODO : Remplacer par les méthodes des nouvelles classes
            //FirstCompressionMethod compress1 = new FirstCompressionMethod(commandLineArgs);

        // Cas où l'utilisateur appelle le programme sans arguments
        }else{
            IntegerArray info1 = new IntegerArray(new int[]{1, 2, 3, 4, 5});
            FirstCompression compress1 = new FirstCompression();
            compress1.compress(info1);
            compress1.decompress(info1);

            IntegerArray info2 = new IntegerArray(new int[]{15, 24, 33, 2});
            compress1.compress(info2);
            compress1.get(2, info2);
        }

    }
}
