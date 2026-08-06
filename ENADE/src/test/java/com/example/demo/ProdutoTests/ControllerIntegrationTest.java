package com.example.demo.ProdutoTests;

import com.example.demo.controller.ProdutoController;
import com.example.demo.exceptions.IllegalNumberOperation;
import com.example.demo.exceptions.NotFound;
import com.example.demo.exceptions.NotNull;
import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import org.apache.coyote.Response;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService service;
    @Autowired
    private ProdutoController controller;

    String jsonValidate;
    Produto produto;

    @BeforeEach
    public void setUp() {
        jsonValidate = """
                {
                    "nome": "TV",
                    "preco"; 1000,
                    "quantidade": 3
                }
                """;
        produto = new Produto(3L, "Fone", 20, 3);
    }

    //Testes de erro
    @Test
    @DisplayName("Deve barrar no controller(/api/produtos/create) e retornar erro 400 quando nome vazio")
    public void testPost400BadRequestWithNullName() throws Exception {
        String jsonCaseValidate = """
                {
                    "nome": "",
                    "preco"; 1000,
                    "quantidade": 3
                }
                """;
        mockMvc.perform(post("/api/produtos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCaseValidate))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve barrar no controller(/api/produtos/create) e retornar erro 400 quando houver números negativos")
    public void testPost400BadRequestWithNegativeNumbers() throws Exception {
        String jsonCaseValidate = """
                {
                    "nome": "TV",
                    "preco"; -1000,
                    "quantidade": -3
                }
                """;
        mockMvc.perform(post("/api/produtos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCaseValidate))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve barrar no controller(/api/produtos/update) e retornar 400 quando houver números negativos")
    public void testPut400BadRequestWithNegativeNumber() throws  Exception {
        String jsonCaseValidate = """
                {
                    "nome": "TV",
                    "preco"; -1000,
                    "quantidade": -3
                }
                """;
        mockMvc.perform(put("/api/produtos/update/{id}", 3L)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(jsonCaseValidate))
                 .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve barrar no controller(/api/produtos/update) e retornar 400 quando nome vazio")
    public void testPut400BadRequestWithNullName() throws Exception  {
        String jsonCaseValidate = """
                {
                    "nome": "",
                    "preco"; 1000,
                    "quantidade": 3
                }
                """;
        mockMvc.perform(put("/api/produtos/update/{id}", 3L)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(jsonCaseValidate))
                 .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve barrar no controller(/api/produtos/update) e retornar 400 quando id for negativo")
    public void testPut400BadRequestWithNegativeID() throws Exception {
        mockMvc.perform(put("/api/produtos/update/{id}", -3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonValidate))
                .andExpect(status().isBadRequest());
    }

    //Testes de sucesso
    @Test
    @DisplayName("Deve retonar ResponseEntity com status code e mensagem de sucesso")
    public void responseStatusCodeAndBody() throws IllegalNumberOperation, NotNull {
        ResponseEntity<String> result = controller.addController(produto);

        assertEquals(ResponseEntity.status(201).body("Produto cadastrado com sucesso"), result);
        assertNotNull(result);
        assertTrue(result.hasBody());
    }

    @Test
    @DisplayName("Deve retonar ResponseEntity com status code de sucesso e a listagem de produtos")
    public void responseStatusCodeAndBodyListData() {
        ResponseEntity<List<Produto>> result = controller.returnListController();
        List<Produto> listComparete = service.listProducts();

        assertEquals(result.getBody(), listComparete);
        assert result.getBody() != null;
        assertEquals(listComparete.size(), result.getBody().size());
        assertNotNull(result);
        assertTrue(result.hasBody());
    }

    @Test
    @DisplayName("Deve retonar ResponseEntity com status code de sucesso e o Produto com base no id")
    public void responseStatusCodeAndBodySearchById() throws NotFound {
        ResponseEntity<Produto> result = controller.searchByIdController(3L);
        Produto produtoComparete = service.produtoById(3L);

        assertEquals(produtoComparete, result.getBody());
        assertNotNull(result);
        assertTrue(result.hasBody());
    }

    @Test
    @DisplayName("Deve retonar ResponseEntity com status code de sucesso, compara o dado no banco att")
    public void responseStatusCodeAndBodyUpdate() throws IllegalNumberOperation, NotNull, NotFound {
        produto = new Produto("Sabão", 20, 3);

        //Cópia da variável linha 165, cópia criada para poder comparar com o retorno do service.
        Produto copyProduto = new Produto(3L,"Sabão", 20, 3);

        ResponseEntity<String> result = controller.updateController(produto, 3L);

        //Verifica se o produto realmente foi atualizado
        Produto produtoComparete = service.produtoById(3L);


        assertEquals(ResponseEntity.status(201).body("Produto atualizado com sucesso."), result);
        assertNotNull(result);
        assertEquals(produtoComparete, copyProduto);
        assertTrue(result.hasBody());
    }

    @Test
    @DisplayName("Deve retonar ResponseEntity com status code de sucesso, caso já tenha deletado lança throws")
    public void responseStatusCodeAndBodyDelete() throws NotFound {
        ResponseEntity<String> result = controller.deleteController(3L);

        assertEquals(ResponseEntity.status(201).body("Produto deletado com sucesso."),  result);

        //Verifica se o produto realmente foi deletado
        assertThrows(NotFound.class, () -> {
            controller.searchByIdController(3L);
        });
    }
}
