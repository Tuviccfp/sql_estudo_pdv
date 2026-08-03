package com.example.demo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;


@Getter
@Setter
class Produto {
    private Long id;
    private String nome;
    private int preco;

    public Produto(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }
    public Produto(Long id, String nome, int preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
    public Produto() {}
}

class RepositoryProduto {
    private final JdbcTemplate jdbcTemplate;

    public RepositoryProduto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Produto> listProduct() {
        String sql = "SELECT * FROM produto";
        return jdbcTemplate.query(sql, useRowMapper);
    }

    public int createProduct(@NonNull Produto produto) {
        String sql = "INSERT INTO produto (nome, preco) values (?, ?)";
        return jdbcTemplate.update(sql, produto.getNome(), produto.getPreco());
    }

    private final RowMapper<Produto> useRowMapper = (rs, rowNum) -> {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getInt("preco"));
        return p;
    };
}

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RepositoryProdutoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    RepositoryProduto repositoryProduto;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Long id = 1L;
        String nome = "Motorola";
        int preco = 1000;
        produto = new Produto(id, nome, preco);
    }

    @Test
    public void createTest() {

    }
}
