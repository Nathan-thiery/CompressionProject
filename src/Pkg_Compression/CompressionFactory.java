package Pkg_Compression;

import java.util.HashMap;

public class CompressionFactory {
    private static final HashMap<CompressionLevel, CompressionStrategy> methods = new HashMap();

    public static CompressionStrategy getCompression(CompressionLevel type){
        if(!methods.containsKey(type)){
            switch (type){
                case FIRST :
                    methods.put(type, new FirstCompression());
                    break;
                case SECOND :
                    methods.put(type, new SecondCompression());
                    break;
                case THIRD :
                    methods.put(type, new ThirdCompression());
                    break;
                default:
                    throw new IllegalArgumentException("Type de compression non supporté: " + type);
            }
        }
        return methods.get(type);
    }

}
