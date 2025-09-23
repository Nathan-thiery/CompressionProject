package Pkg_MathsForCompression;

import Pkg_Logger.Writter;


public class CompressionMethods {

    public int max(int[] arr){
        if(arr == null || arr.length ==0){
            Writter.warning_log("Integer Array given to Pkg_MathsForCompression.CompressionMethods.max(int[] arr) is empty.");
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

    public int divArrondiSup(int num, int den){
        return (num + den -1) / den;
    }

}
