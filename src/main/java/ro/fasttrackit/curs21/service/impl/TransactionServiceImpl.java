package ro.fasttrackit.curs21.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs21.entity.TransactionEntity;
import ro.fasttrackit.curs21.exceptions.TransactionNotFoundException;
import ro.fasttrackit.curs21.model.TransactionModel;
import ro.fasttrackit.curs21.model.TransactionType;
import ro.fasttrackit.curs21.repository.TransactionRepository;
import ro.fasttrackit.curs21.service.TransactionService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public List<TransactionModel> getAll(String type, Double minAmount, Double maxAmount) {
        List<TransactionEntity> entities;
        if (!isNull(type) && !isNull(minAmount) && !isNull(maxAmount)) {
            entities = repository.findByTypeAndAmountGreaterThanEqualAndAmountLessThanEqual(TransactionType.safeValueOf(type),
                    minAmount, maxAmount);
        } else if (!isNull(type) && !isNull(minAmount)) {
            entities = repository.findByTypeAndAmountGreaterThanEqual(TransactionType.safeValueOf(type), minAmount);
        } else if (!isNull(type) && !isNull(maxAmount)) {
            entities = repository.findByTypeAndAmountLessThanEqual(TransactionType.safeValueOf(type), maxAmount);
        } else if (!isNull(minAmount) && !isNull(maxAmount)) {
            entities = repository.
                    findByAmountGreaterThanEqualAndAmountLessThanEqual(minAmount, maxAmount);
        } else if (!isNull(type)) {
            entities = repository.findByType(TransactionType.safeValueOf(type));
        } else if (!isNull(minAmount)) {
            entities = repository.findByAmountGreaterThanEqual(minAmount);
        } else if (!isNull(maxAmount)) {
            entities = repository.findByAmountLessThanEqual(maxAmount);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(entity -> modelMapper.map(entity, TransactionModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionModel findById(long id) {
        TransactionEntity entity = getTransactionById(id);
        return modelMapper.map(entity, TransactionModel.class);
    }

    private TransactionEntity getTransactionById(long id) {
        return repository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public TransactionModel addTransaction(TransactionModel transactionModel) {
        TransactionEntity entity = modelMapper.map(transactionModel, TransactionEntity.class);
        repository.save(entity);
        return modelMapper.map(entity, TransactionModel.class);
    }

    @Override
    public TransactionModel updateTransaction(long id, TransactionModel transactionModel) {
        TransactionEntity entity = getTransactionById(id);
        entity.setProduct(transactionModel.getProduct());
        entity.setType(TransactionType.safeValueOf(transactionModel.getType()));
        entity.setAmount(transactionModel.getAmount());
        repository.save(entity);
        return modelMapper.map(entity, TransactionModel.class);
    }

    @Override
    public TransactionModel patchTransaction(long id, TransactionModel transactionModel) {
        TransactionEntity entity = getTransactionById(id);
        entity.setAmount(transactionModel.getAmount() != 0 ? transactionModel.getAmount() : entity.getAmount());
        entity.setProduct(ofNullable(transactionModel.getProduct()).orElse(entity.getProduct()));
        repository.save(entity);
        return modelMapper.map(entity, TransactionModel.class);
    }

    @Override
    public TransactionModel delete(long id) {
        TransactionEntity entity = getTransactionById(id);
        repository.delete(entity);
        return modelMapper.map(entity, TransactionModel.class);
    }

    @Override
    public Map<TransactionType, List<TransactionModel>> getGroupedByType() {
        List<TransactionEntity> entities = repository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, TransactionModel.class))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(type -> TransactionType.safeValueOf(type.getType())));
    }

    @Override
    public Map<String, List<TransactionModel>> getGroupedByProduct() {
        List<TransactionEntity> entities = repository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, TransactionModel.class))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(TransactionModel::getProduct));
    }

}
