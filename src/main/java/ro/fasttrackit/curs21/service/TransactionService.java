package ro.fasttrackit.curs21.service;

import ro.fasttrackit.curs21.model.TransactionModel;
import ro.fasttrackit.curs21.model.TransactionType;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<TransactionModel> getAll(String type, Double minAmount, Double maxAmount);

    TransactionModel findById(long id);

    TransactionModel addTransaction(TransactionModel transactionModel);

    TransactionModel updateTransaction(long id, TransactionModel transactionModel);

    TransactionModel patchTransaction(long id, TransactionModel transactionModel);

    TransactionModel delete(long id);

    Map<TransactionType, List<TransactionModel>> getGroupedByType();

    Map<String, List<TransactionModel>> getGroupedByProduct();
}
