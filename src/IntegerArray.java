public class IntegerArray {
    private int[] value;
    private int minimalByteSize;

    public IntegerArray(int[] ints){
        // Logs
        Writter.info_log("\nConstruction of an IntegerArray");
        for(int i = 0 ; i < ints.length ; i++){
            Writter.fine_log("\tvalue["+ i +"] = " + "{" + ints[i] + "}");
        }
        Writter.fine_log("\tminimalByteSize = -1\n");

        // Changes
        this.value = ints;
        this.minimalByteSize = -1;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        // Logs
        Writter.finer_log("\nChanging IntegerArray value");
        Writter.finest_log("\tPrevious value : ");
        for(int i=0;i<this.value.length;i++){
            Writter.finest_log("\t\tvalue["+ i + "] = {" + this.value[i] + "} = {" + Integer.toBinaryString(this.value[i]) + "}");
        }

        // Changes
        this.value = value;

        // Logs
        Writter.finest_log("\n\tActual value : ");
        for(int i=0;i<this.value.length;i++){
            Writter.finest_log("\t\tvalue["+ i + "] = {" + this.value[i] + "} = {" + Integer.toBinaryString(this.value[i]) + "}");
        }
    }

    public int getMinimalByteSize() {
        return minimalByteSize;
    }

    public void setMinimalByteSize(int minimalByteSize) {
        // Logs
        Writter.finer_log("Changing IntegerArray minimalByteSize from [" + this.minimalByteSize + "] to [" + minimalByteSize + "]");

        this.minimalByteSize = minimalByteSize;
    }
}
