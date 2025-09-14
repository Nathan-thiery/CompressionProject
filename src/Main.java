public class Main {

    public static void main(String args[]){
        Writter.setCurrent_Log_Level(LogLevel.FINEST);

        // Cas où l'utilisateur entre ses propres entiers
        if(args.length > 0){

            int[] commandLineArgs = new int[args.length];
            for (int i = 0; i < args.length; i++){
                commandLineArgs[i] = Integer.parseInt(args[i]);
            }
            FirstCompressionMethod compress1 = new FirstCompressionMethod(commandLineArgs);

        // Cas où l'utilisateur appelle le programme sans arguments
        }else{

            FirstCompressionMethod compress1 = new FirstCompressionMethod(new int[]{1, 2, 3, 4, 5});

        }

    }
}
