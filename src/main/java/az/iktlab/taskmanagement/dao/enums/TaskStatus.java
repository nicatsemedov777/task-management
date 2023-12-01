package az.iktlab.taskmanagement.dao.enums;

public enum TaskStatus {
    TO_DO,
    IN_PROGRESS,
    IN_REVIEW,
    DONE;

    public static boolean match(String text) {
        for (var status : TaskStatus.values()) {
            if (status.name().equals(text))
                return true;
        }
        return false;
    }

    public static TaskStatus getByLabel(String text) {
        for (var status : TaskStatus.values()) {
            if (status.name().equals(text))
                return status;
        }
        return null;
    }
}
