package Pkg_Compression;

public interface CompressionStrategy {
    void compress(IntegerArray values);
    void decompress(IntegerArray values);
    int get(int i, IntegerArray values);
}
