package com.example.demo.repository;

import com.example.demo.exceptions.NotFound;
import com.example.demo.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepoImpl implements ProdutoRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Produto> rowMapper = (rs, rowNum) -> {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getInt("preco"));
        p.setQuantidade(rs.getInt("quantidade"));
        return p;
    };

    @Override
    public int createProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, produto.getNome(), produto.getPreco(), produto.getQuantidade());
    }

    @Override
    public int updated(Produto produto, long id) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";
        return jdbcTemplate.update(sql, produto.getNome(), produto.getPreco(), produto.getQuantidade(), id);
    }

    @Override
    public List<Produto> listProduts() {
        String sql = "SELECT * FROM produto ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int deleteProdut(Long id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Produto catchProdutById(Long id) throws NotFound {
        try {
            String sql = "SELECT id, nome, preco, quantidade FROM produto WHERE id = ?";
            return (Produto) jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFound("Produto não encontrado");
        }
    }
}
