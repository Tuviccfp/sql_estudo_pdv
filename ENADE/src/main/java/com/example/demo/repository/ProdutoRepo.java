package com.example.demo.repository;

import com.example.demo.model.Produto;

import java.util.List;

public interface ProdutoRepo {
    int createProduto(Produto produto);
    int updated(Produto produto, long id);
    List<Produto> listProduts();
    int deleteProdut(Long id);
    Produto catchProdutById(Long id);
}
