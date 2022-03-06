package ro.fasttrackit.curs21.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs21.dto.TransactionDTO;
import ro.fasttrackit.curs21.model.TransactionType;

import java.util.List;
import java.util.Map;

@RequestMapping("/transactions")
public interface TransactionAPi {

    @GetMapping(path = "")
    ResponseEntity<List<TransactionDTO>> getAll(@RequestParam(required = false) String type,
                                                @RequestParam(required = false) Double minAmount,
                                                @RequestParam(required = false) Double maxAmount);

    @GetMapping(path = "/{id}")
    ResponseEntity<TransactionDTO> getById(@PathVariable long id);

    @PostMapping(path = "")
    ResponseEntity<TransactionDTO> addTransaction(@RequestBody TransactionDTO transaction);

    @PutMapping(path = "/{id}")
    ResponseEntity<TransactionDTO> update(@PathVariable long id,
                                          @RequestBody TransactionDTO transaction);

    @PatchMapping(path = "/{id}")
    ResponseEntity<TransactionDTO> patchTransaction(@PathVariable long id,
                                                    @RequestBody TransactionDTO transaction);

    @DeleteMapping(path = "/{id}")
    ResponseEntity<TransactionDTO> delete(@PathVariable long id);

    @GetMapping(path = "/reports/type")
    ResponseEntity<Map<TransactionType, List<TransactionDTO>>> getTransactionByType();

    @GetMapping(path = "/reports/product")
    ResponseEntity<Map<String, List<TransactionDTO>>> getTransactionByProduct();
}
