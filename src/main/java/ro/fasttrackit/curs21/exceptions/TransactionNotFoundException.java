package ro.fasttrackit.curs21.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){
        super("Transaction not found!");
    }
}
