package com.loja.offroad.repository;

import com.loja.offroad.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefoneAndIdNot(String telefone, Long id);
}