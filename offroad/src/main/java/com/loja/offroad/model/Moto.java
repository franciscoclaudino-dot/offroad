package com.loja.offroad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "motos")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A marca é obrigatória")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÿ ]+$",
            message = "A marca deve conter apenas letras"
    )
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÿ0-9 ]+$",
            message = "O modelo possui caracteres inválidos"
    )
    private String modelo;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1900, message = "Digite um ano válido")
    @Max(value = 2100, message = "Digite um ano válido")
    private Integer ano;

    @NotNull(message = "A cilindrada é obrigatória")
    @Positive(message = "A cilindrada deve ser maior que zero")
    private Integer cilindrada;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "O estoque é obrigatório")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    private Integer estoque;

    public Moto() {
    }

    public Moto(
            String marca,
            String modelo,
            Integer ano,
            Integer cilindrada,
            Double preco,
            Integer estoque) {

        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cilindrada = cilindrada;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(Integer cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
}