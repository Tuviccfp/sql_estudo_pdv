package com.example.demo.controller;

import com.example.demo.exceptions.IllegalNumberOperation;
import com.example.demo.exceptions.NotFound;
import com.example.demo.exceptions.NotNull;
import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/create")
    public ResponseEntity<String> addController(@Valid @RequestBody Produto produto) throws IllegalNumberOperation, NotNull {
        service.createProduct(produto);
        return ResponseEntity.status(201).body("Produto cadastrado com sucesso");
    }

    @GetMapping("/")
    public ResponseEntity<List<Produto>> returnListController() {
        List<Produto> produtoList = service.listProducts();
        return ResponseEntity.status(200).body(produtoList);
    }

    @GetMapping("/catch-unique/{id}")
    public ResponseEntity<Produto> searchByIdController(
            @Valid
            @Positive(message = "Não é permitido valores negativos")
            @PathVariable("id") Long id
    ) throws NotFound {
            Produto p = service.produtoById(id);
            return ResponseEntity.status(200).body(p);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateController(
            @Valid @RequestBody Produto produto,
            @Valid
            @Positive(message = "Não é permitido valores negativos")
            @PathVariable("id") Long id
    ) throws IllegalNumberOperation, NotNull {
            service.updateProduct(produto, id);
            return ResponseEntity.status(201).body("Produto atualizado com sucesso.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteController(
            @Valid
            @Positive(message = "Não é permitido valores negativos")
            @PathVariable("id") Long id
            ) throws NotFound {
            service.deleteProduct(id);
            return ResponseEntity.status(201).body("Produto deletado com sucesso.");
    }
}
