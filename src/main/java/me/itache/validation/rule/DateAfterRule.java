package me.itache.validation.rule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 */
public class DateAfterRule implements Rule {
    private LocalDate beforeDate;
    private String parsePattern;

    public DateAfterRule(LocalDate beforeDate, String parsePattern) {
        this.beforeDate = beforeDate;
        this.parsePattern = parsePattern;
    }

    @Override
    public boolean validate(String data) {
        if(data == null) {
            return false;
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(parsePattern);
            return beforeDate.isBefore(LocalDate.parse(data, df));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
