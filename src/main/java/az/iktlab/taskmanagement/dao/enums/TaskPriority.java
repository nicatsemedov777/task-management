package az.iktlab.taskmanagement.dao.enums;

public enum TaskPriority {
    NOT_URGENT,
    MEDIUM,
    NORMAL,
    MAJOR,
    HIGH,
    URGENT,
    BLOCKER;

    public static boolean match(String text) {
        for (var task : TaskPriority.values()) {
            if (task.name().equals(text))
                return true;
        }
        return false;
    }

    public static TaskPriority getByLabel(String text) {
        for (var task : TaskPriority.values()) {
            if (task.name().equals(text))
                return task;
        }
        return null;
    }
}
