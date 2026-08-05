package com.example.demo.service;

import com.example.demo.exceptions.IllegalNumberOperation;
import com.example.demo.exceptions.NotFound;
import com.example.demo.exceptions.NotNull;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepo repo;
    public ProdutoService(ProdutoRepo repo) {
        this.repo = repo;
    }
    private void validate(Produto produto) throws NotNull, IllegalNumberOperation {
        if(produto.getNome() == null) {
            throw new NotNull("Não é permitido campos nulos");
        } else if(produto.getPreco() <= 0 || produto.getQuantidade() <= 0) {
            throw new IllegalNumberOperation("Não é permitido valores abaixo ou igual a zero.");
        }
    }
    public int createProduct(Produto produto) throws IllegalNumberOperation, NotNull {
        validate(produto);
        return repo.createProduto(produto);
    }
    public List<Produto> listProducts() {
        return repo.listProduts();
    }
    public Produto produtoById(Long id) {
        return repo.catchProdutById(id);
    }
    public int updateProduct(Produto produto, Long id) throws NotNull, IllegalNumberOperation {
        validate(produto);
        return repo.updated(produto, id);
    }
    public void deleteProduct(Long id) throws NotFound {
        if(id == null) {
            throw new NotFound("Não foi informado um ID.");
        }

    }
}
