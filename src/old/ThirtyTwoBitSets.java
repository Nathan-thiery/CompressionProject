/*
package old;

import java.util.BitSet;

public class ThirtyTwoBitSets {
    private BitSet[] bs;

    public ThirtyTwoBitSets(int[] ints, int CompressedBitSize) {
        int i;
        int actualNbBitSet;

        // Number of paquet of 32 Bytes to create in order to be able to stock everything
        final int lastNbBitSet ;
        if(ints.length > 32){
            lastNbBitSet = divArrondiSup((32 / CompressedBitSize), (ints.length / CompressedBitSize));
        }else{lastNbBitSet = 1;}

        Writter.info_log("\nIntegers given are supposed to be distributed into [" + lastNbBitSet + "] paquets of Size 32 bytes.");
        // -1 because array starts from 0
        bs = new BitSet[lastNbBitSet];
        // Declaration of 32 Bytes paquets
        for(i = 0 ; i <= lastNbBitSet-1 ; i++){
            bs[i] = new BitSet(32);
        }
        Writter.fine_log("Integers represented by consecutive [" + CompressedBitSize + "] bytes in an array of total [" + ints.length + "] bytes.");


        for(actualNbBitSet = 0 ; actualNbBitSet < lastNbBitSet ; actualNbBitSet ++){
            Writter.finer_log("\tConstruction of paquet [" + actualNbBitSet + "] out of [" + (lastNbBitSet-1) + "]");
            if(32 % CompressedBitSize != 0){
                int lastIntegerOffset = 32 - (32%CompressedBitSize);
                Writter.finest_log("\t[" + lastIntegerOffset + "] out of [32] bytes CAN be used in paquet [" + actualNbBitSet + "].");
                Writter.finest_log("\t[" + ((ints.length - (32 * actualNbBitSet))>lastIntegerOffset ? lastIntegerOffset : ints.length - (32 * actualNbBitSet)) + "] out of [32] bytes WILL be used in paquet [" + actualNbBitSet + "].");
                for(i=0 ; i < lastIntegerOffset && (i + (31 * actualNbBitSet)) < ints.length; i++){
                    Writter.finest_log("\t\tPaquet["+actualNbBitSet+"]["+(31-i)+"] = [" + ints[i+(31*actualNbBitSet)] + "]");
                    bs[actualNbBitSet].set(31 - i, ints[i + (31*actualNbBitSet)] != 0);
                }
                if(i < 31 ){
                    for(i=i ; i <=31 ; i++){
                        bs[actualNbBitSet].set(31 - i, false);
                    }
                }
            }else{
                for(i = 0 ; i <= 32 ; i++){
                    bs[actualNbBitSet].set(32 -i, ints[i + (32 * actualNbBitSet)]);
                }
            }
        }

        Writter.info_log("End of the creation of [" + lastNbBitSet + "] paquet(s) of 32 Bytes.");


        for(i=0; i < lastNbBitSet ; i++){
            Writter.fine_log("\n\tPaquet[" + i + "] - 32 Bytes");
                Writter.finer_log("\t\t> 1 2 3 4 5 6 7 8 9 1 1 2 3 4 5 6 7 8 9 2 1 2 3 4 5 6 7 8 9 3 1 2");
                Writter.finer_log("\t\t> " + bitSetToString(bs[i], 31));
        }


    }

    public static String bitSetToString(BitSet bs, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = size; i >= 0; i--) {
            sb.append(bs.get(i) ? "1 " : "0 ");
        }
        return sb.toString();
    }

    private int divArrondiSup(int num, int den){
        return (num + den -1) / den;
    }
}
*/