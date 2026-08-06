package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Produto {
    private long id;

    @NotBlank(message = "Não é permitido valores em branco")
    private String nome;

    @Positive(message = "Não é permitido valores negativos ou 0")
    private int preco;

    @Positive(message = "Não é permitido valores negativos ou 0")
    private int quantidade;

    public Produto(String nome, int preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(long id, String nome, int preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto() {}
}
