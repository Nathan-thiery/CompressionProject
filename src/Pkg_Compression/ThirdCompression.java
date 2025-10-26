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
        SplitResult splitInts = separateNormalFromHighIntegers(ints);

        // Calculating Compression parameters
        CompressionMethods CmprMthd = new CompressionMethods();
        int a_size = Integer.SIZE - Integer.numberOfLeadingZeros(Collections.max(splitInts.normal));
        int b_size = Integer.SIZE - Integer.numberOfLeadingZeros(Collections.max(splitInts.highOutliers));
        int i = splitInts.normal.size();
        int j = splitInts.highOutliers.size();
        int max_a = CmprMthd.divArrondiSup(i,(32/a_size));
        int max_b = CmprMthd.divArrondiSup(j,(32/b_size));
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

        // @TODO : Compressing both lists into an int[]
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
        for (int i_index=0 ; i_index < compressedInts.length ; i_index++) {
            Writter.fine_log("\t > Paquet [" + i_index + "]/[" + (compressedInts.length -1) + "]");
            Writter.finer_log("\t\t-- " + String.format("%32s", Integer.toBinaryString(compressedInts[i_index])).replace(' ', '0') + " --");
        }
        Writter.info_log("\n\t\t\t[End of Array Compression Using Third Compressing Method]\n\n\n");

        // @TODO : Updating minimalByteSize with a, b and index_OF value

    }

    @Override
    public void decompress(IntegerArray values) {
        ;
    }

    @Override
    public int get(int i, IntegerArray values) {
        return 0;
    }

    // Split the integers in two groups, normal integers and high integers using percentile calculation
    private static SplitResult separateNormalFromHighIntegers(int[] ints){
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
