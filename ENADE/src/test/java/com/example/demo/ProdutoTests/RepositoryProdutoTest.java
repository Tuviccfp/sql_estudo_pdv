package com.example.demo.ProdutoTests;

import com.example.demo.exceptions.IllegalNumberOperation;
import com.example.demo.exceptions.NotNull;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
class Produto {
    private Long id;
    private String nome;
    private int preco;
    private int quantidade;

    public Produto(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }
    public Produto(Long id, String nome, int preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    public Produto() {}
}

class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto createProduct(Produto produto) throws NotNull, IllegalNumberOperation{
        if(produto.getNome() == null) {
            throw new NotNull("Não é permitido valores nulos");
        } else if(produto.getPreco() <= 0 || produto.getQuantidade() <= 0) {
            throw new IllegalNumberOperation("Zero e números abaixo de zero não são permitidos");
        }
        return repository.createProduto(produto);
    }
    public List<Produto> listProduto() {
        return repository.listProduto();
    }
    public Produto updatedProduct(Produto produto) throws NotNull, IllegalNumberOperation {
        if(produto.getNome() == null) {
            throw new NotNull("Não é permitido valores nulos");
        } else if(produto.getPreco() <= 0 || produto.getQuantidade() <= 0) {
            throw new IllegalNumberOperation("Zero e números abaixo de zero não são permitidos");
        }

        produto.setId(2L);
        produto.setNome("Fones de ouvido");
        produto.setPreco(300);
        produto.setQuantidade(3);

        return repository.updateProduto(produto);
    }

    public void deleteProduct(Long id) throws IllegalNumberOperation {
        Produto search = listProduto().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalNumberOperation("Não foi possível encontrar."));
        repository.deleteProduto(search);
    }
}

@ExtendWith(MockitoExtension.class)
public class RepositoryProdutoTest {

    @Mock
    ProdutoRepository repo;

    @InjectMocks
    ProdutoService service;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        Long id = 1L;
        String nome = "Motorola";
        int preco = 1000;
        int quantidade = 1;
        produto = new Produto(id, nome, preco, quantidade);
    }

    @Test
    @DisplayName("Deve registrar e retornar um produto com sucesso quando os dados forem válidos")
    public void createTest() throws IllegalNumberOperation, NotNull {
        when(repo.createProduto(any(Produto.class))).thenReturn(produto);

        Produto produtoSalvo = service.createProduct(produto);

        assertNotNull(produtoSalvo);
        assertEquals(produto, produtoSalvo);
        verify(repo, times(1)).createProduto(produto);
    }

    @Test
    @DisplayName("Deve tentar salvar e lança um erro de NotNull")
    public void createInvalidTestWithNotNull() {
        produto = new Produto(null, null, 0, 0);

        NotNull exception = assertThrows(NotNull.class, () -> {
            service.createProduct(produto);
        });

        assertEquals("Não é permitido valores nulos", exception.getMessage());
        verify(repo, never()).createProduto(any());
    }

    @Test
    @DisplayName("Deve tentar salvar e lança um erro de IllegalNumberOperation")
    public void createInvalidTestWithIllegalNumberOperation() {
        produto = new Produto(3L, "Mouse sem fio", 0, 0);

        IllegalNumberOperation exception = assertThrows(IllegalNumberOperation.class, () -> {
            service.createProduct(produto);
        });

        assertEquals("Zero e números abaixo de zero não são permitidos", exception.getMessage());
        verify(repo, never()).createProduto(any());
    }

    @Test
    @DisplayName("Deve atualizar e retornar um produto com sucesso")
    public void updateCaseTest() throws IllegalNumberOperation, NotNull {
        Produto newProduto = new Produto(3L,"Balinha", 10, 2);

        when(repo.updateProduto(newProduto)).thenReturn(newProduto);

        Produto produtoUpdated = service.updatedProduct(newProduto);

        assertNotNull(produtoUpdated);
        assertEquals(produtoUpdated, newProduto);

        verify(repo, times(1)).updateProduto(newProduto);
    }

    @Test
    @DisplayName("Deve buscar e deletar o dado com sucesso")
    public void deleteCaseTest() throws IllegalNumberOperation {
        Produto newProduto = new Produto(3L,"Balinha", 10, 2);
        List<Produto> produtoList = new ArrayList<>();
        produtoList.add(produto);
        produtoList.add(newProduto);

        when(repo.listProduto()).thenReturn(produtoList);

        service.deleteProduct(3L);

        verify(repo, times(1)).listProduto();
        verify(repo, times(1)).deleteProduto(newProduto);
        verify(repo, never()).deleteProduto(produto);
    }
}
