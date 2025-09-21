

/*

public class FirstCompressionMethod {
    private ThirtyTwoBitSets paquetsOf32;

    public FirstCompressionMethod(int[] ints) {
        int i;
        // Espace commentaires
        Writter.fine_log("Construction of old.FirstCompressionMethod class :");
        for(i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        int maxOneBit = Integer.SIZE - Integer.numberOfLeadingZeros(max(ints));
        Writter.info_log("\nnumberOfTrailingZeros of the Highest Number is positionned as bit N°" + maxOneBit);

        //@TODO : Représenter les entiers de 'ints' en chaine de maxOneBit dans un array d'entier (Ex : 3 = [00101], ainsi de suite)
        int[] tabOfInts = extractRightBitsArray(ints, maxOneBit);
        Writter.info_log("\nTableau créé pour être passé à old.ThirtyTwoBitSets");


        //@TODO : Construire un tableau de paquets de 32 Bits avec l'appel de la classe old.ThirtyTwoBitSets
        paquetsOf32 = new ThirtyTwoBitSets(tabOfInts, maxOneBit);

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

    // Méthode pour traiter un tableau complet
    private int[] extractRightBitsArray(int[] arr, int x) {
        int[] result = new int[arr.length * x];
        for (int i = 0; i < arr.length; i++) {
            Writter.fine_log("\nInt[" + arr[i] + "] - Range [" + (i*x) + ";" + (i*x + (x-1)) + "]");
            for(int j = 0 ; j < x ; j++){
                result[i * x + j] = (arr[i] >> (x-1) - j) & 1;
                Writter.finer_log("\t >" + result[i * x + j] + "<");
            }
        }
        return result;
    }


}


 */