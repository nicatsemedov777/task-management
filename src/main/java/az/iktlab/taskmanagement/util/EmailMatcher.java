package az.iktlab.taskmanagement.util;

import jakarta.validation.ConstraintValidatorContext;
import liquibase.util.StringUtil;

import java.util.regex.Pattern;

public class EmailMatcher {
    public static final String EMAIL_PATTERN = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\])";


    public static boolean match(String email) {
        if (email == null) {
            return false;
        }
        if (StringUtil.isEmpty(email)) {
            return false;
        }
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}
