package Pkg_Logger;

public enum LogLevel {
    SEVERE(7),
    WARNING(6),
    INFO(5),
    CONFIG(4),
    FINE(3),
    FINER(2),
    FINEST(1);

    private final int priority;

    LogLevel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
