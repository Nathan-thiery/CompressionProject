package Pkg_Compression;

import Pkg_MathsForCompression.CompressionMethods;
import Pkg_MathsForCompression.CompressionMethods.*;
import Pkg_Logger.Writter;
import java.util.*;

public class ThirdCompression implements CompressionStrategy{


    public static record SplitResult(List<Integer> normal, List<Integer> highOutliers){}

    @Override
    public void compress(IntegerArray values) {
        int[] ints = values.getValue();

        // Logs
        Writter.info_log("\n\n\n\t\t\t[Compressing Using Third Compressing Method]");
        for(int i=0; i < ints.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + ints[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + "]");
            Writter.finer_log("\t\tnumberOfTrailingZeros = " + (Integer.SIZE - Integer.numberOfLeadingZeros(ints[i])));
        }

        // Splitting normal and high values
        Writter.finest_log("\n\t\t\t-- Splitting list between normal and high integers .... --");
        SplitResult splitInts = splitByBitWidthPFOR(ints, 0.8);

        // Calculating Compression parameters
        Writter.finest_log("\t\t\t-- Caculating compression parameters .... --\n");
        CompressionMethods CmprMthd = new CompressionMethods();
        if(!splitInts.normal.isEmpty() && !splitInts.highOutliers.isEmpty()) {
            int a_size = Integer.SIZE - Integer.numberOfLeadingZeros(Collections.max(splitInts.normal));
            int b_size = Integer.SIZE - Integer.numberOfLeadingZeros(Collections.max(splitInts.highOutliers));
            int i = splitInts.normal.size();
            int j = splitInts.highOutliers.size();
            int max_a = CmprMthd.divArrondiSup(i, (32 / a_size));
            int max_b = CmprMthd.divArrondiSup(j, (32 / b_size));
            int arraySize = max_a + max_b;


            // Logs
            Writter.fine_log("\n\t[Compression parameters infos]");
            Writter.finer_log("\t\ta_size (number of minimal bits to represent integers of group A) = " + a_size);
            Writter.finer_log("\t\tb_size (number of minimal bits to represent integers of group B) = " + b_size);
            Writter.finer_log("\t\ti (number of integers in group A) = " + i);
            Writter.finer_log("\t\tj (number of integers in group B) = " + j);
            Writter.finer_log("\t\tmax_a (number of paquets of 32 bytes to represent every integer of group A) = " + max_a);
            Writter.finer_log("\t\tmax_b (number of paquets of 32 bytes to represent every integer of group B) = " + max_b);
            Writter.finer_log("\t\tarraySize (Total size of the integerArray, group A & B included) = " + arraySize);
            Writter.finer_log("\t\tIntegers in group A : ");
            Writter.finer_log("\t\t" + splitInts.normal.toString());
            Writter.finer_log("\t\tIntegers in group B : ");
            Writter.finer_log("\t\t" + splitInts.highOutliers.toString());

            int[] compressedInts = new int[arraySize];

            Writter.finest_log("\n\t\t\tCompressing Group A values");
            // Compressing normal values (Group A, from Tab[0] to Tab[a_size -1])
            int bitPos = 0;
            for (int val : splitInts.normal) {
                for (int i_index = 0; i_index < a_size; i_index++) {
                    int bit = (val >> i_index) & 1;
                    int index = bitPos / ((32 / a_size) * a_size);
                    int offset = bitPos % ((32 / a_size) * a_size);
                    compressedInts[index] |= (bit << offset);
                    bitPos++;
                }
            }
            Writter.finest_log("\t\t\tCompressing Group B values\n");
            // Compressing highOutliers values (Group B, from Tab[a_size] to Tab[arraySize])
            bitPos = 0;
            for (int val : splitInts.highOutliers) {
                for (int j_index = 0; j_index < b_size; j_index++) {
                    int bit = (val >> j_index) & 1;
                    int index = bitPos / ((32 / b_size) * b_size);
                    int offset = bitPos % ((32 / b_size) * b_size);
                    compressedInts[index + max_a] |= (bit << offset);
                    bitPos++;
                }
            }


            // Logs again
            for (int i_index = 0; i_index < compressedInts.length; i_index++) {
                Writter.fine_log("\t > Paquet [" + i_index + "]/[" + (compressedInts.length - 1) + "]");
                Writter.finer_log("\t\t-- " + String.format("%32s", Integer.toBinaryString(compressedInts[i_index])).replace(' ', '0') + " --");
            }
            Writter.info_log("\n\t\t\t[End of Array Compression Using Third Compressing Method]\n\n\n");


            values.setValue(compressedInts);
            values.setMinimalByteSize(compressParameters(a_size, b_size, max_a));
        }else{
            // @TODO : Gérer cas ou une des deux listes est vide
            Writter.warning_log("Failure to split datas in two groups based on their values : \n\tTable with 'Normal' integers of size [" + splitInts.normal.size() + "]\n\tTable with 'High outliers' integers of size [" + splitInts.highOutliers.size() + "]");
            System.exit(1);
        }
    }

    @Override
    public void decompress(IntegerArray values) {
        int[] compressedInts;
        int minimalByteSize;
        int i;

        compressedInts = values.getValue();
        minimalByteSize = values.getMinimalByteSize();

        // Logs
        Writter.info_log("\n\n\n\t\t\t[Decompressing Using Third Decompressing Method]\n");
        for(i=0; i < compressedInts.length ; i++){
            Writter.fine_log( "\tInteger [" + i + "] = {" + compressedInts[i] + "}");
            Writter.finer_log("\t\t[" + String.format("%32s", Integer.toBinaryString(compressedInts[i])).replace(' ', '0') + "]");
        }

        int a_size = extractA(minimalByteSize);
        int b_size = extractB(minimalByteSize);
        int index_OF = extractIndexOF(minimalByteSize);


        // Parameters for Group A
        int lastBits_a = 32 - Integer.numberOfLeadingZeros(compressedInts[index_OF-1]);
        if (lastBits_a == 0) lastBits_a = 32;
        // int totalBits_a = (index_OF-1) * 32 + lastBits_a;
        int Nbvalues_a = (32/a_size)*(index_OF-1) + Math.ceilDiv(lastBits_a,a_size);
        Writter.config_log("Nombre de valeurs de 0 à (index_OF - 2) <=> 0 à " + (index_OF-2) + " = " + ((32/a_size)*(index_OF-1)));
        Writter.config_log("Nombre de valeurs dans la dernière case du groupe A <=> " + (index_OF - 1) + " = " + Math.ceilDiv(lastBits_a,a_size));
        // Parameters for Group B
        int lastBits_b = 32 - Integer.numberOfLeadingZeros(compressedInts[compressedInts.length-1]);
        if (lastBits_b == 0) lastBits_b = 32;
        // int totalBits_b = (compressedInts.length-1 - (index_OF-1)) * 32 + lastBits_b;
        int Nbvalues_b = (32/b_size)*((compressedInts.length-1) - index_OF) + Math.ceilDiv(lastBits_b , b_size);
        Writter.config_log("Nombre de valeurs de Index_OF à (tab.length-2) <=> " + index_OF + " a "+ (compressedInts.length-2) + " = " + ((32/b_size)*((compressedInts.length -1)- index_OF)));
        Writter.config_log("Nombre de valeurs dans la dernière case du groupe B <=> " + (compressedInts.length-1) + " = " + Math.ceilDiv(lastBits_b,b_size));

        // TODO : Fix length pb
        // Logs
        Writter.info_log("\n\t\t> Group A : Total of [" + Nbvalues_a + "] different values of size {" + a_size + "} splitted between [" + index_OF + "] Paquets of 32 bytes.");
        Writter.info_log("\t\t> Group B : Total of [" + Nbvalues_b + "] different values of size {" + b_size + "} splitted between [" + (compressedInts.length - index_OF) + "] Paquets of 32 bytes.");
        Writter.finer_log("\n\t\t-- Starting Decompression Algorithm --");

        int[] ints = new int[Nbvalues_a + Nbvalues_b];
        int bitPos = 0;
        for (i = 0; i < Nbvalues_a; i++) {
            int value = 0;
            for (int b = 0; b < a_size; b++) {
                int index = bitPos / ((32 / a_size) * a_size);
                int offset = bitPos % ((32 / a_size) * a_size);
                int bit = (compressedInts[index] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }
            ints[i] = value;
        }

        bitPos = 0;
        for (int j = 0; j < Nbvalues_b; j++) {
            int value = 0;
            for (int b = 0; b < b_size; b++) {
                int index = bitPos / ((32 / b_size) * b_size);
                int offset = bitPos % ((32 / b_size) * b_size);
                int bit = (compressedInts[index + index_OF] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }
            ints[j + i] = value;
        }

        // Logs again
        for (i=0 ; i < ints.length ; i++) {
            Writter.fine_log("\t > Paquet [" + i + "]/[" + (ints.length -1) + "]");
            Writter.finer_log("\t\t-- " + String.format("%32s", Integer.toBinaryString(ints[i])).replace(' ', '0') + " --");
        }
        Writter.info_log("\n\t\t\t[End of Array Decompressing Using Second Method]\n\n\n");


        values.setValue(ints);
        values.setMinimalByteSize(0);
    }

    @Override
    public int get(int i, IntegerArray values) {
        int minimalByteSize = values.getMinimalByteSize();
        int[] ints = values.getValue();
        int value = 0;


        int a_size = extractA(minimalByteSize);
        int b_size = extractB(minimalByteSize);
        int index_OF = extractIndexOF(minimalByteSize);

        // Parameters for Group A
        int lastBits_a = 32 - Integer.numberOfLeadingZeros(ints[index_OF-1]);
        if (lastBits_a == 0) lastBits_a = 32;
        int totalBits_a = (index_OF-1) * 32 + lastBits_a;
        int Nbvalues_a = (int) ((totalBits_a - (index_OF-1)* (32%((32/a_size) * a_size))) / a_size);

        // Parameters for Group B
        int lastBits_b = 32 - Integer.numberOfLeadingZeros(ints[ints.length-1]);
        if (lastBits_b == 0) lastBits_b = 32;
        int totalBits_b = (ints.length-1 - index_OF) * 32 + lastBits_b;
        int Nbvalues_b = (int) ((totalBits_b - (ints.length -1 - index_OF)* (32%((32/b_size) * b_size))) / b_size);

        int bitPos = -1;
        if(i<=Nbvalues_a-1) {
            bitPos = i * a_size;

            for (int b = 0; b < a_size; b++) {
                int index = bitPos / ((32 / a_size) * a_size);
                int offset = bitPos % ((32 / a_size) * a_size);
                int bit = (ints[index] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }

        }else if(i <= Nbvalues_a + Nbvalues_b-1){
            bitPos = (i-Nbvalues_a) * b_size;

            for (int b = 0; b < b_size; b++) {
                int index = bitPos / ((32 / b_size) * b_size);
                int offset = bitPos % ((32 / b_size) * b_size);
                int bit = (ints[index+index_OF] >>> offset) & 1;
                value |= (bit << b);
                bitPos++;
            }
        }else{
            Writter.warning_log("\n\ti value out of bounds\n");
            System. exit(0);
        }

        Writter.fine_log("\n\n\n\t\tValue at position [" + i + "] of Compressed Array = " + value + "\n\n\n");
        return value;
    }

    // Split the integers in two groups, normal integers and high integers using percentile calculation
    private static SplitResult separateNormalFromHighIntegers(int[] ints){
        // @TODO : Améliorer l'algorithme de différenciation de l'appartenance à une liste ou à une autre
        // @TODO : Méthodes possibles : PFOR -> Patched Frame-of-Reference
        List<Integer> listValues = new ArrayList<>();
        for (int v : ints) listValues.add(v);
        Collections.sort(listValues);

        // Quartiles calculation
        double q1 = getPercentile(listValues, 25);
        double q3 = getPercentile(listValues, 75);
        double iqr = q3 - q1;

        // Threshold separating normal from high numbers
        double upperBound = q3 + 1.5 * iqr;

        List<Integer> normal = new ArrayList<>();
        List<Integer> highOutliers = new ArrayList<>();

        // Splitting values in both Lists depending on the threshold
        for (int v : ints) {
            if (v > upperBound) {
                highOutliers.add(v);
            } else {
                // Both low and normal numbers
                normal.add(v);
            }
        }

        return new SplitResult(normal, highOutliers);
    }

    public static SplitResult splitByBitWidthPFOR(int[] values, double percentile) {
        if (values == null || values.length == 0)
            return new SplitResult(Collections.emptyList(), Collections.emptyList());

        int n = values.length;
        int[] bitWidths = new int[n];

        // nombre de bits nécessaires pour chaque entier
        for (int i = 0; i < n; i++) {
            int v = Math.abs(values[i]); // gestion des négatifs (même bitwidth que valeur absolue)
            bitWidths[i] = (v == 0) ? 1 : (32 - Integer.numberOfLeadingZeros(v));
        }

        // Sseuil de bits
        int[] sortedBits = bitWidths.clone();
        Arrays.sort(sortedBits);
        int cutoffIndex = (int) Math.floor(percentile * (n - 1));
        int bitThreshold = sortedBits[cutoffIndex];

        List<Integer> normal = new ArrayList<>();
        List<Integer> highOutliers = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (bitWidths[i] <= bitThreshold)
                normal.add(values[i]);
            else
                highOutliers.add(values[i]);
        }

        if (normal.isEmpty()) {
            normal.add(highOutliers.remove(0));
        } else if (highOutliers.isEmpty()) {
            highOutliers.add(normal.remove(normal.size() - 1));
        }

        Writter.fine_log("PFOR split (by bit width): threshold = " + bitThreshold + " bits, normals = " + normal.size() + ", outliers = " + highOutliers.size());

        return new SplitResult(normal, highOutliers);
    }

    // Compress 3 ints into one, with a & b on 6 bytes, index_OF on 20 bytes.
    private static int compressParameters(int a, int b, int index_OF){
        return (a << 26) | (b << 20) | index_OF;
    }

    // Extract value a from an int with already compressed parameters inside
    private static int extractA(int compressedParameters) {
        return (compressedParameters >>> 26) & 0b111111;
    }

    // Extract value b from an int with already compressed parameters inside
    private static int extractB(int compressedParameters) {
        return (compressedParameters >>> 20) & 0b111111;
    }

    // Extract value index_OF from an int with already compressed parameters inside
    private static int extractIndexOF(int compressedParameters) {
        return compressedParameters & 0xFFFFF;
    }

    // Calculate a percentile
    private static double getPercentile(List<Integer> sortedValues, double percentile) {
        if (sortedValues.isEmpty()) return 0;
        int n = sortedValues.size();
        double index = (percentile / 100.0) * (n - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);
        if (lower == upper) {
            return sortedValues.get(lower);
        }
        return sortedValues.get(lower) + (index - lower) * (sortedValues.get(upper) - sortedValues.get(lower));
    }

}
