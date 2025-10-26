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
        Writter.info_log("\n\n\n\t\t\t[Compressing Using Second Compressing Method]");
        for(i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        // Minimal number of bytes to represents each number given in the ints Array.
        CompressionMethods CmprMthd = new CompressionMethods();
        int minimalCompressedSize = Integer.SIZE - Integer.numberOfLeadingZeros(CmprMthd.max(ints));
        Writter.info_log("\n\t\t> Minimal number of bytes to represent the highest number >" + minimalCompressedSize + "<");

        // Number of paquet of 32 Bytes to create in order to be able to stock every int
        final int lastNbBitSet ;
        lastNbBitSet = CmprMthd.divArrondiSup(ints.length, (32 / minimalCompressedSize));
        Writter.info_log("\t\t> Every number included in the Array given (of length " + ints.length + ") will be stocked in [" + lastNbBitSet + "] paquet(s) of 32 bytes.\n");

        // Allocation of CompressedInts Array
        int[] compressedInts = new int[lastNbBitSet];

        // TODO : Changer la compression ( [32 / minimalCompressedSize] val par paquet de 32 bits.)
        // Remplissage des 0 en 32 % ((32/minimalCompressedSize) * minimalCompressedSize)

        // Compression
        int bitPos = 0;
        for (int val : ints) {
            for (int b = 0; b < minimalCompressedSize; b++) {
                int bit = (val >> b) & 1;
                int index = bitPos / ((32 / minimalCompressedSize) * minimalCompressedSize);
                int offset = bitPos % ((32 / minimalCompressedSize) * minimalCompressedSize);
                compressedInts[index] |= (bit << offset);
                bitPos++;
            }
        }

        // Logs again
        for (i=0 ; i < compressedInts.length ; i++) {
            Writter.fine_log("\t > Paquet [" + i + "]/[" + (compressedInts.length -1) + "]");
            Writter.finer_log("\t\t-- " + String.format("%32s", Integer.toBinaryString(compressedInts[i])).replace(' ', '0') + " --");
        }
        Writter.info_log("\n\t\t\t[End of Array Compression Using Second Compressing Method]\n\n\n");

        values.setValue(compressedInts);
        values.setMinimalByteSize(minimalCompressedSize);
    }

    @Override
    public void decompress(IntegerArray values) {
        int[] compressedInts;
        int minimalByteSize;
        int i;

        compressedInts = values.getValue();
        minimalByteSize = values.getMinimalByteSize();

        // Logs
        Writter.info_log("\n\n\n\t\t\t[Decompressing Using Second Decompressing Method]\n");
        for(i=0; i < compressedInts.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + compressedInts[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(compressedInts[i])).replace(' ', '0') + "]");
        }

        int lastBits = 32 - Integer.numberOfLeadingZeros(compressedInts[compressedInts.length-1]);
        if (lastBits == 0) lastBits = 32;
        int totalBits = (compressedInts.length - 1) * 32 + lastBits;
        int Nbvalues = (int) ((totalBits - (compressedInts.length-1)* (32%((32/minimalByteSize) * minimalByteSize))) / minimalByteSize);

        Writter.info_log("\n\t\t> Total of [" + Nbvalues + "] different values of size {" + minimalByteSize + "} splitted between [" + compressedInts.length + "] Paquets of 32 bytes.");
        Writter.finer_log("\n\t\t-- Starting Decompression Algorithm --");

        int[] ints = new int[Nbvalues];
        int bitPos = 0;
        for (i = 0; i < Nbvalues; i++) {
            int value = 0;
            for (int b = 0; b < minimalByteSize; b++) {
                int index = bitPos / ((32 / minimalByteSize) * minimalByteSize);
                int offset = bitPos % ((32 / minimalByteSize) * minimalByteSize);
                int bit = (compressedInts[index] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }
            ints[i] = value;
        }

        // Logs again
        for (i=0 ; i < ints.length ; i++) {
            Writter.fine_log("\t > Paquet [" + i + "]/[" + (ints.length -1) + "]");
            Writter.finer_log("\t\t-- " + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + " --");
        }
        Writter.info_log("\n\t\t\t[End of Array Decompressing Using Second Method]\n\n\n");

        values.setValue(ints);
        values.setMinimalByteSize(-1);
    }

    @Override
    public int get(int i, IntegerArray values) {
        int minimalByteSize = values.getMinimalByteSize();
        int[] ints = values.getValue();
        int value = 0;
        int bitPos = i * minimalByteSize;

        for (int b = 0; b < minimalByteSize; b++) {
            int index = bitPos / ((32 / minimalByteSize) * minimalByteSize);
            int offset = bitPos % ((32 / minimalByteSize) * minimalByteSize);
            int bit = (ints[index] >>> offset) & 1;
            value |= (bit << b);
            bitPos++;
        }

        Writter.fine_log("\n\n\n\t\tValue at position [" + i + "] of Compressed Array = " + value + "\n\n\n");
        return value;
    }

}
