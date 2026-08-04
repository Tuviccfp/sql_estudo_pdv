package com.example.demo.ProdutoTests;

import java.util.List;

public interface ProdutoRepository {
    Produto createProduto(Produto produto);
    List<Produto> listProduto();
    Produto updateProduto(Produto produto);
    void deleteProduto(Produto produto);
}
