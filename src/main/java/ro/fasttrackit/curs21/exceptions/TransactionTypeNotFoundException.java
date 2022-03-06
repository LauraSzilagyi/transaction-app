package ro.fasttrackit.curs21.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.fasttrackit.curs21.model.TransactionType;

import java.util.Arrays;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionTypeNotFoundException extends RuntimeException{
    public TransactionTypeNotFoundException(){
    super("Transaction type should be: " + Arrays.toString(TransactionType.values()));
    }
}