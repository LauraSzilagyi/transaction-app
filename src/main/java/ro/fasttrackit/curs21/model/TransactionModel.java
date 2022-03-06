package ro.fasttrackit.curs21.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {

    private long id;
    private String product;
    private String type;
    private double amount;

    public void setType(String type) {
        this.type = type.toUpperCase();
    }
}
