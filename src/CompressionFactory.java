import java.util.HashMap;

public class CompressionFactory {
    private static final HashMap<CompressionType, CompressionStrategy> methods = new HashMap();

    public static CompressionStrategy getCompression(CompressionType type){
        if(!methods.containsKey(type)){
            switch (type){
                case FIRST :
                    methods.put(type, new FirstCompression());
                    break;
                case SECOND :

                    break;
                case THIRD :

                    break;
                default:
                    throw new IllegalArgumentException("Type de compression non supporté: " + type);
            }
        }
        return methods.get(type);
    }


    public enum CompressionType {
        FIRST, SECOND, THIRD
    }
}
