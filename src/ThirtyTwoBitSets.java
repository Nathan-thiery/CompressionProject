import java.util.BitSet;

public class ThirtyTwoBitSets {
    private BitSet[] bs;

    public ThirtyTwoBitSets(int[] ints, int CompressedBitSize) {
        int i;
        int actualNbBitSet;

        // Number of paquet of 32 Bytes to create in order to be able to stock everything
        final int lastNbBitSet = divArrondiSup((32 / CompressedBitSize), (ints.length / CompressedBitSize));
        Writter.info_log("\nIntegers given are supposed to be distributed into [" + lastNbBitSet + "] paquets of Size 32 bytes.");
        // -1 because array starts from 0
        bs = new BitSet[lastNbBitSet-1];
        // Declaration of 32 Bytes paquets
        for(i = 0 ; i < lastNbBitSet-1 ; i++){
            bs[i] = new BitSet(32);
        }
        Writter.fine_log("Integers represented by consecutive [" + CompressedBitSize + "] bytes in an array of total [" + ints.length + "] bytes.\n\t>> " + ints.toString() + " <<");


        for(actualNbBitSet = 0 ; actualNbBitSet < lastNbBitSet ; actualNbBitSet ++){
            if(32 % CompressedBitSize != 0){
                int lastIntegerOffset = 32 - (32%CompressedBitSize);
                for(i=0 ; i <= lastIntegerOffset ; i++){
                    bs[actualNbBitSet].set(32 - i, ints[i + (32*actualNbBitSet)]);
                }
                if(i <= 32 ){
                    for(i=i ; i <=32 ; i++){
                        bs[actualNbBitSet].set(32 - i, false);
                    }
                }
            }else{
                for(i = 0 ; i <= 32 ; i++){
                    bs[actualNbBitSet].set(32 -i, ints[i + (32 * actualNbBitSet)]);
                }
            }
        }

    }

    private int divArrondiSup(int num, int den){
        return (num + den -1) / den;
    }
}
