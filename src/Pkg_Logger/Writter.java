package Pkg_Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.nio.file.Path;



enum ANSI{
    // Codes Pkg_Logger.ANSI
    RESET("\u001B[0m"),
    ROUGE("\u001B[31m"),             // rouge
    ORANGE("\u001B[38;5;208m"),      // orange
    VIOLET("\u001B[35m"),            // violet
    BLEU_CLAIR("\u001B[94m"),        // bleu clair
    BLEU_FONCE("\u001B[34m"),        // bleu foncé
    VERT_CLAIR("\u001B[92m"),        // vert clair
    VERT_FONCE("\u001B[32m");        // vert foncé

    private final String color;

    ANSI(String color){
        this.color = color;
    }

    @Override
    public String toString(){
        return color;
    }

}

public class Writter {
    private static LogLevel current_Log_Level = LogLevel.INFO;

    public static void setCurrent_Log_Level(LogLevel loglvl){
        current_Log_Level = loglvl;
    }

    public static void log(LogLevel loglvl, String msg){
        boolean res = loglvl.getPriority() >= current_Log_Level.getPriority();
        switch(res? 1 : 0){
            case 1 :
                System.out.println(msg);
            case 0 :
                return;
        }
    }

    public static void file_log(String msg){
        Path path = Path.of("Benchmarks/By size/ThirdMethod_AllNumbers.txt");
        List<String> lines = List.of(msg);
        try {
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void severe_log(String msg){log(LogLevel.SEVERE, ANSI.ROUGE + msg + ANSI.RESET);}
    public static void warning_log(String msg){log(LogLevel.WARNING,ANSI.ORANGE + msg + ANSI.RESET);}
    public static void info_log(String msg){log(LogLevel.INFO, ANSI.VIOLET + msg + ANSI.RESET);}
    public static void config_log(String msg){log(LogLevel.CONFIG, ANSI.BLEU_CLAIR + msg + ANSI.RESET);}
    public static void fine_log(String msg){log(LogLevel.FINE, ANSI.BLEU_FONCE + msg + ANSI.RESET);}
    public static void finer_log(String msg){log(LogLevel.FINER, ANSI.VERT_CLAIR + msg + ANSI.RESET);}
    public static void finest_log(String msg){log(LogLevel.FINEST, ANSI.VERT_FONCE + msg + ANSI.RESET);}

}
