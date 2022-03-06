package ro.fasttrackit.curs21.model;

import ro.fasttrackit.curs21.exceptions.TransactionTypeNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

public enum TransactionType {
    SELL,
    BUY;

    public static TransactionType safeValueOf(String type) {
        try {
            return TransactionType.valueOf(type.toUpperCase().trim());
        } catch (Exception ignored) {
            throw new TransactionTypeNotFoundException();
        }
    }
}
