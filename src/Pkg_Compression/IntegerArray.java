package Pkg_Compression;

import Pkg_Logger.Writter;

public class IntegerArray {
    private int[] value;
    private int minimalByteSize;

    public IntegerArray(int[] ints){
        // Logs
        Writter.info_log("\n\n\n\t\t\t[Construction of an Pkg_Compression.IntegerArray]\n");
        for(int i = 0 ; i < ints.length ; i++){
            Writter.fine_log("\tvalue["+ i +"] = " + "{" + ints[i] + "}");
        }
        Writter.fine_log("\tminimalByteSize = -1\n");

        // Changes
        this.value = ints;
        this.minimalByteSize = -1;
        Writter.info_log("\t\t\t[End of construction of Pkg_Compression.IntegerArray]\n\n\n");
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        // Logs
        Writter.info_log("\n\n\n\t\t\t[Changing Pkg_Compression.IntegerArray value]\n");
        Writter.fine_log("\tPrevious value : ");
        for(int i=0;i<this.value.length;i++){
            Writter.fine_log("\t\tvalue["+ i + "] = ");
            Writter.finer_log("\t\t\t{" +this.value[i] + "} = {" + String.format("%32s", Integer.toBinaryString(this.value[i])).replace(' ', '0') + "}");
        }

        // Changes
        this.value = value;

        // Logs
        Writter.fine_log("\n\tActual value : ");
        for(int i=0;i<this.value.length;i++){
            Writter.fine_log("\t\tvalue["+ i + "] = ");
            Writter.finer_log("\t\t\t{"+ this.value[i] + "} = {" + String.format("%32s", Integer.toBinaryString(this.value[i])).replace(' ', '0') + "}");
        }
        Writter.info_log("\n\t\t\t[End of change of Pkg_Compression.IntegerArray value]\n\n\n");
    }

    public int getMinimalByteSize() {
        return minimalByteSize;
    }

    public void setMinimalByteSize(int minimalByteSize) {
        // Logs
        Writter.info_log("\n\n\n\t\t\t-- Changing Pkg_Compression.IntegerArray minimalByteSize from [" + this.minimalByteSize + "] to [" + minimalByteSize + "] --\n\n\n");

        this.minimalByteSize = minimalByteSize;
    }
}
