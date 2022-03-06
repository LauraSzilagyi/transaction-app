package ro.fasttrackit.curs21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.curs21.entity.TransactionEntity;
import ro.fasttrackit.curs21.model.TransactionType;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByTypeAndAmountGreaterThanEqual(TransactionType type, Double minAmount);

    List<TransactionEntity> findByTypeAndAmountGreaterThanEqualAndAmountLessThanEqual(TransactionType type,
                                                                                      Double minAmount,
                                                                                      Double maxAmount);

    List<TransactionEntity> findByTypeAndAmountLessThanEqual(TransactionType type, Double maxAmount);

    List<TransactionEntity> findByAmountGreaterThanEqualAndAmountLessThanEqual(Double minAmount, Double maxAmount);

    List<TransactionEntity> findByType(TransactionType type);

    List<TransactionEntity> findByAmountGreaterThanEqual(Double minAmount);

    List<TransactionEntity> findByAmountLessThanEqual(Double maxAmount);
}
