package ro.fasttrackit.curs21.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.fasttrackit.curs21.api.TransactionAPi;
import ro.fasttrackit.curs21.dto.TransactionDTO;
import ro.fasttrackit.curs21.model.TransactionModel;
import ro.fasttrackit.curs21.model.TransactionType;
import ro.fasttrackit.curs21.service.TransactionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController implements TransactionAPi {

    private final TransactionService service;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<TransactionDTO>> getAll(String type, Double minAmount, Double maxAmount) {
        List<TransactionModel> models = service.getAll(type, minAmount, maxAmount);
        List<TransactionDTO> response = models.stream()
                .map(model -> modelMapper.map(model, TransactionDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionDTO> getById(long id) {
        TransactionModel model = service.findById(id);
        TransactionDTO response = modelMapper.map(model, TransactionDTO.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionDTO> addTransaction(TransactionDTO transaction) {
        TransactionModel transactionModel = modelMapper.map(transaction, TransactionModel.class);
        TransactionModel model = service.addTransaction(transactionModel);
        TransactionDTO response = modelMapper.map(model, TransactionDTO.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionDTO> update(long id, TransactionDTO transaction) {
        TransactionModel transactionModel = modelMapper.map(transaction, TransactionModel.class);
        TransactionModel model = service.updateTransaction(id, transactionModel);
        TransactionDTO response = modelMapper.map(model, TransactionDTO.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionDTO> patchTransaction(long id, TransactionDTO transaction) {
        TransactionModel transactionModel = modelMapper.map(transaction, TransactionModel.class);
        TransactionModel model = service.patchTransaction(id, transactionModel);
        TransactionDTO response = modelMapper.map(model, TransactionDTO.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionDTO> delete(long id) {
        TransactionModel model = service.delete(id);
        TransactionDTO response = modelMapper.map(model, TransactionDTO.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<TransactionType, List<TransactionDTO>>> getTransactionByType() {
        Map<TransactionType, List<TransactionModel>> serviceData = service.getGroupedByType();
        Map<TransactionType, List<TransactionDTO>> response = new HashMap<>();
        serviceData.forEach((k, v) -> response.put(k, mapToTransactionDTO(v)));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, List<TransactionDTO>>> getTransactionByProduct() {
        Map<String, List<TransactionModel>> serviceData = service.getGroupedByProduct();
        Map<String, List<TransactionDTO>> response = new HashMap<>();
        serviceData.forEach((k, v) -> response.put(k, mapToTransactionDTO(v)));
        return ResponseEntity.ok(response);
    }

    private List<TransactionDTO> mapToTransactionDTO(List<TransactionModel> transactionModels) {
        return transactionModels.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }
}
