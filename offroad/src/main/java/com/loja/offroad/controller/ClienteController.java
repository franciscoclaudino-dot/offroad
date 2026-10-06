package com.loja.offroad.controller;

import com.loja.offroad.model.Cliente;
import com.loja.offroad.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "clientes",
                clienteRepository.findAll()
        );

        return "clientes";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "cliente",
                new Cliente()
        );

        return "form-cliente";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result) {

        if (result.hasErrors()) {
            return "form-cliente";
        }

        Long id = cliente.getId();

        if (id == null) {
            id = -1L;
        }

        if (clienteRepository.existsByCpfAndIdNot(
                cliente.getCpf(),
                id)) {

            result.rejectValue(
                    "cpf",
                    "cpf.duplicado",
                    "Este CPF já está cadastrado."
            );

            return "form-cliente";
        }

        if (clienteRepository.existsByEmailAndIdNot(
                cliente.getEmail(),
                id)) {

            result.rejectValue(
                    "email",
                    "email.duplicado",
                    "Este email já está cadastrado."
            );

            return "form-cliente";
        }

        if (clienteRepository.existsByTelefoneAndIdNot(
                cliente.getTelefone(),
                id)) {

            result.rejectValue(
                    "telefone",
                    "telefone.duplicado",
                    "Este telefone já está cadastrado."
            );

            return "form-cliente";
        }

        // Salva o cliente
        clienteRepository.save(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Cliente cliente = clienteRepository
                .findById(id)
                .orElse(null);

        model.addAttribute(
                "cliente",
                cliente
        );

        return "form-cliente";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id) {

        clienteRepository.deleteById(id);

        return "redirect:/clientes";
    }
}