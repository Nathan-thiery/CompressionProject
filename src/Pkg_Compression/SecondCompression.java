package Pkg_Compression;

import Pkg_Logger.Writter;
import Pkg_Logger.LogLevel;
import Pkg_MathsForCompression.CompressionMethods;
import Pkg_MathsForCompression.CompressionMethods.*;


public class SecondCompression implements CompressionStrategy{
    @Override
    public void compress(IntegerArray values) {
        int[] ints;
        int i;

        ints = values.getValue();

        // Logs
        Writter.fine_log("Compressing :");
        for(i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        // Minimal number of bytes to represents each number given in the ints Array.
        CompressionMethods CmprMthd = new CompressionMethods();
        int minimalCompressedSize = Integer.SIZE - Integer.numberOfLeadingZeros(CmprMthd.max(ints));
        Writter.info_log("\nMinimal number of bytes to represent the highest number >" + minimalCompressedSize + "<\n");

        // Number of paquet of 32 Bytes to create in order to be able to stock every int
        final int lastNbBitSet ;
        lastNbBitSet = CmprMthd.divArrondiSup(ints.length, (32 / minimalCompressedSize));
        Writter.info_log("Every number included in the Array given (of length " + ints.length + ") will be stocked in [" + lastNbBitSet + "] paquet(s) of 32 bytes.");

        // Allocation of CompressedInts Array
        int[] compressedInts = new int[lastNbBitSet];

        // TODO : Changer la compression ( [32 / minimalCompressedSize] val par paquet de 32 bits.)
        // Remplissage des 0 en 32 % ((32/minimalCompressedSize) * minimalCompressedSize)
        // Compression
        int bitPos = 0;
        for (int val : ints) {
            for (int b = 0; b < minimalCompressedSize; b++) {
                int bit = (val >> b) & 1;
                int index = bitPos / 32;
                int offset = bitPos % 32;
                compressedInts[index] |= (bit << offset);
                bitPos++;
            }
        }

        // Logs again
        for (i=0 ; i < compressedInts.length ; i++) {
            Writter.info_log("\n\t > Paquet [" + i + "]/[" + (compressedInts.length -1) + "]" + "\n\t\t-- " + String.format("%32s", Integer.toBinaryString(compressedInts[i])).replace(' ', '0') + " --");
        }

        values.setValue(compressedInts);
        values.setMinimalByteSize(minimalCompressedSize);
    }

    @Override
    public void decompress(IntegerArray values) {
        ;
    }

    @Override
    public int get(int i, IntegerArray values) {
        return 0;
    }

}
