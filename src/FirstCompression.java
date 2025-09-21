// Compression method which can write a compressed form of one integer on 2 consecutiv integers.
public class FirstCompression implements CompressionStrategy{

    public FirstCompression() {
        ;
    }

    @Override
    public void compress(IntegerArray values) {
        int i;
        int[] ints;

        ints = values.getValue();

        // Logs
        Writter.fine_log("Compressing :");
        for(i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        // Minimal number of bytes to represents each number given in the ints Array.
        int minimalCompressedSize = Integer.SIZE - Integer.numberOfLeadingZeros(max(ints));
        Writter.info_log("\nMinimal number of bytes to represent the highest number >" + minimalCompressedSize + "<\n");

        // Number of paquet of 32 Bytes to create in order to be able to stock every int
        final int lastNbBitSet ;
        if(ints.length > 32){
            lastNbBitSet = divArrondiSup((32 / minimalCompressedSize), (ints.length / minimalCompressedSize));
        }else{lastNbBitSet = 1;}

        Writter.info_log("Every number included in the Array given (of length " + ints.length + ") will be stocked in [" + lastNbBitSet + "] paquet(s) of 32 bytes.");
        int[] compressedInts = new int[lastNbBitSet];

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
        String paquetValueString;
        for (i=0 ; i < compressedInts.length ; i++) {
            Writter.info_log("\n\t > Paquet [" + i + "]/[" + (compressedInts.length -1) + "]" + "\n\t\t-- " + Integer.toBinaryString(compressedInts[i]) + " --");
        }

        values.setValue(compressedInts);
        values.setMinimalByteSize(minimalCompressedSize);
    }

    @Override
    public void decompress(IntegerArray values) {
        int[] compressedInts;
        int minimalByteSize;

        // TODO : Vérifier si l'instanciation des variables agit comme une copie ou comme un lien direct vers l'objet IntegerArray donné en paramètre.
        compressedInts = values.getValue();
        minimalByteSize = values.getMinimalByteSize();

        int lastBits = 32 - Integer.numberOfLeadingZeros(compressedInts[compressedInts.length-1]);
        if (lastBits == 0) lastBits = 32;
        int totalBits = (compressedInts.length - 1) * 32 + lastBits;
        int Nbvalues = (int) (totalBits / minimalByteSize);

        int[] ints = new int[Nbvalues];
        int bitPos = 0;
        for (int i = 0; i < Nbvalues; i++) {
            int value = 0;
            for (int b = 0; b < minimalByteSize; b++) {
                int index = bitPos / 32;
                int offset = bitPos % 32;
                int bit = (compressedInts[index] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }
            ints[i] = value;
        }

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
            int index = bitPos / 32;
            int offset = bitPos % 32;
            int bit = (ints[index] >>> offset) & 1;
            value |= (bit << b);
            bitPos++;
        }

        Writter.fine_log("\nValue compressed at position [" + i + "] = " + value);
        return value;
    }

    public int max(int[] arr){
        if(arr == null || arr.length ==0){
            Writter.warning_log("Integer Array given to old.FirstCompressionMethod is empty.");
            throw new IllegalArgumentException();
        }

        int max = arr[0];
        for(int i = 1 ; i < arr.length ; i ++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }

    private int divArrondiSup(int num, int den){
        return (num + den -1) / den;
    }

}
