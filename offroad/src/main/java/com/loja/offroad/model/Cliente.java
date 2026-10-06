package com.loja.offroad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(
        name = "clientes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cpf"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "telefone")
        }
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÿ ]+$",
            message = "O nome deve conter apenas letras e espaços"
    )
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(
            regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
            message = "O CPF deve conter 11 números"
    )
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(
            regexp = "^[0-9()\\-+\\s]{10,20}$",
            message = "Digite um telefone válido"
    )
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Digite um email válido")
    private String email;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}