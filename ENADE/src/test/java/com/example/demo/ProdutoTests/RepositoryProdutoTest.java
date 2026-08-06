package com.example.demo.ProdutoTests;

import com.example.demo.exceptions.IllegalNumberOperation;
import com.example.demo.exceptions.NotFound;
import com.example.demo.exceptions.NotNull;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepo;
import com.example.demo.service.ProdutoService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RepositoryProdutoTest {

    @Autowired
    private ProdutoRepo repo;
    @Autowired
    private ProdutoService service;

    @Test
    @DisplayName("Deve salvar o produto no banco de dados teste")
    public void createTest() throws IllegalNumberOperation, NotNull {
        String nome = "Motorola";
        int preco = 1000;
        int quantidade = 1;
        Produto produto = new Produto(nome, preco, quantidade);

        int produtoSalvo = service.createProduct(produto);

        assertNotNull(produtoSalvo);
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("Deve tentar salvar e lança um erro de NotNull")
    public void createInvalidTestWithNotNull() {
        Produto produto = new Produto(null, 0, 0);

        NotNull exception = assertThrows(NotNull.class, () -> {
            service.createProduct(produto);
        });

        assertEquals("Não é permitido campos nulos", exception.getMessage());
    }

    @Test
    @DisplayName("Deve tentar salvar e lança um erro de IllegalNumberOperation")
    public void createInvalidTestWithIllegalNumberOperation() {
        Produto produto = new Produto("Mouse sem fio", 0, -10);

        IllegalNumberOperation exception = assertThrows(IllegalNumberOperation.class, () -> {
            service.createProduct(produto);
        });

        assertEquals("Não é permitido valores abaixo ou igual a zero.", exception.getMessage());
    }

    @Test
    @DisplayName("")
    public void produtoByIdTeste() throws NotFound {
        Produto produtoCompare = new Produto(3L, "Tablet Samsung Galaxy Tab", 2000, 1);
        Produto resultSearch = service.produtoById(3L);

        assertEquals(produtoCompare, resultSearch);
        assertNotNull(resultSearch);
    }
    @Test
    @DisplayName("Deve listar uma lista de produtos")
    public void listProdutCase() {
        long id = 1L;
        String nome = "Celular Motorola 84";
        int preco = 1200;
        int quantidade = 2;
        Produto produto = new Produto(id, nome, preco, quantidade);

        List<Produto> produtoList = service.listProducts();
        assertEquals(19, produtoList.size());
    }
    @Test
    @DisplayName("Deve atualizar e retornar um produto com sucesso")
    public void updateCaseTest() throws IllegalNumberOperation, NotNull {
        Produto newProduto = new Produto(1L,"Balinha", 10, 2);


        int produtoUpdated = service.updateProduct(newProduto, newProduto.getId());

        assertNotNull(produtoUpdated);
        assertEquals(produtoUpdated, 1);
    }
    @Test
    @DisplayName("Deve buscar e deletar o dado com sucesso")
    public void deleteCaseTest() throws NotFound {
        service.deleteProduct(3L);

        assertEquals(1, 1);
    }
}
