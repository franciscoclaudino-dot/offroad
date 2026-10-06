package com.loja.offroad.controller;

import com.loja.offroad.model.Moto;
import com.loja.offroad.repository.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoController {

    private final MotoRepository motoRepository;

    public MotoController(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "motos",
                motoRepository.findAll()
        );

        return "motos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "moto",
                new Moto()
        );

        return "form-moto";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("moto") Moto moto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "form-moto";
        }

        motoRepository.save(moto);

        return "redirect:/motos";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Moto moto = motoRepository
                .findById(id)
                .orElse(null);

        model.addAttribute(
                "moto",
                moto
        );

        return "form-moto";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id) {

        motoRepository.deleteById(id);

        return "redirect:/motos";
    }
}