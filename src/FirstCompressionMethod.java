import java.util.BitSet;


public class FirstCompressionMethod {
    private BitSet bits;

    public FirstCompressionMethod(int[] ints) {
        // Espace commentaires
        Writter.fine_log("Construction of FirstCompressionMethod class :");
        for(int i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        int maxOneBit = Integer.SIZE - Integer.numberOfLeadingZeros(max(ints));
        Writter.info_log("\nnumberOfTrailingZeros of the Highest Number is positionned as bit N°" + maxOneBit);

        //bits = new BitSet(32);


    }

    public int max(int[] arr){
        if(arr == null || arr.length ==0){
            Writter.warning_log("Integer Array given to FirstCompressionMethod is empty.");
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


}
