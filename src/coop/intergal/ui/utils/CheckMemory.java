package coop.intergal.ui.utils;

import java.util.ArrayList;
import java.util.List;

public class CheckMemory {
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static void main(String[] args) {
    
    }
    public static void check()
    {
        // I assume you will know how to create a object Person yourself...
//        List<Person> list = new ArrayList<Person>();
//        for (int i = 0; i <= 100000; i++) {
//            list.add(new Person("Jim", "Knopf"));
//        }
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long memory = totalMemory - freeMemory;
        
        
        System.out.println("Memoria (Bytes): " + totalMemory + " libre " + freeMemory + " usada "+ memory );
        System.out.println("Memoria (MB): " + bytesToMegabytes(totalMemory) + " libre " + bytesToMegabytes(freeMemory) + " usada "+ bytesToMegabytes(memory) );
    }
}