package com.loja.offroad.controller;

import com.loja.offroad.model.Moto;
import com.loja.offroad.model.Venda;
import com.loja.offroad.repository.ClienteRepository;
import com.loja.offroad.repository.MotoRepository;
import com.loja.offroad.repository.VendaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final MotoRepository motoRepository;

    public VendaController(
            VendaRepository vendaRepository,
            ClienteRepository clienteRepository,
            MotoRepository motoRepository) {

        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.motoRepository = motoRepository;
    }


    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "vendas",
                vendaRepository.findAll()
        );

        return "vendas";
    }


    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "venda",
                new Venda()
        );

        carregarDadosFormulario(model);

        return "form-venda";
    }


    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("venda") Venda venda,
            Model model) {


        if (venda.getData() == null) {
            venda.setData(LocalDate.now());
        }


        if (venda.getValorTotal() == null ||
                venda.getValorTotal() <= 0) {

            model.addAttribute(
                    "erro",
                    "O valor da venda deve ser maior que zero."
            );

            carregarDadosFormulario(model);

            return "form-venda";
        }


        if (venda.getMoto() == null ||
                venda.getMoto().getId() == null) {

            model.addAttribute(
                    "erro",
                    "Selecione uma moto."
            );

            carregarDadosFormulario(model);

            return "form-venda";
        }


        Moto novaMoto = motoRepository
                .findById(venda.getMoto().getId())
                .orElse(null);

        if (novaMoto == null) {

            model.addAttribute(
                    "erro",
                    "Moto não encontrada."
            );

            carregarDadosFormulario(model);

            return "form-venda";
        }

        if (venda.getId() == null) {

            // Verifica o estoque
            if (novaMoto.getEstoque() == null ||
                    novaMoto.getEstoque() <= 0) {

                model.addAttribute(
                        "erro",
                        "Não é possível realizar a venda. Esta moto está sem estoque."
                );

                carregarDadosFormulario(model);

                return "form-venda";
            }


            novaMoto.setEstoque(
                    novaMoto.getEstoque() - 1
            );

            motoRepository.save(novaMoto);

            venda.setMoto(novaMoto);

            vendaRepository.save(venda);

            return "redirect:/vendas";
        }

        Venda vendaAntiga = vendaRepository
                .findById(venda.getId())
                .orElse(null);

        if (vendaAntiga == null) {

            model.addAttribute(
                    "erro",
                    "Venda não encontrada."
            );

            carregarDadosFormulario(model);

            return "form-venda";
        }

        Moto motoAntiga = vendaAntiga.getMoto();

        boolean trocouMoto =
                motoAntiga == null ||
                        !motoAntiga.getId().equals(
                                novaMoto.getId()
                        );


        if (trocouMoto) {

            if (motoAntiga != null) {

                if (motoAntiga.getEstoque() == null) {
                    motoAntiga.setEstoque(0);
                }

                motoAntiga.setEstoque(
                        motoAntiga.getEstoque() + 1
                );

                motoRepository.save(motoAntiga);
            }


            if (novaMoto.getEstoque() == null ||
                    novaMoto.getEstoque() <= 0) {

                // Reverte a devolução da moto antiga
                if (motoAntiga != null) {

                    motoAntiga.setEstoque(
                            motoAntiga.getEstoque() - 1
                    );

                    motoRepository.save(motoAntiga);
                }

                model.addAttribute(
                        "erro",
                        "Não é possível trocar para esta moto. Ela está sem estoque."
                );

                carregarDadosFormulario(model);

                return "form-venda";
            }

            novaMoto.setEstoque(
                    novaMoto.getEstoque() - 1
            );

            motoRepository.save(novaMoto);
        }

        venda.setMoto(novaMoto);

        vendaRepository.save(venda);

        return "redirect:/vendas";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Venda venda = vendaRepository
                .findById(id)
                .orElse(null);

        if (venda == null) {
            return "redirect:/vendas";
        }

        model.addAttribute(
                "venda",
                venda
        );

        carregarDadosFormulario(model);

        return "form-venda";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id) {

        Venda venda = vendaRepository
                .findById(id)
                .orElse(null);

        if (venda != null) {

            Moto moto = venda.getMoto();

            if (moto != null) {

                if (moto.getEstoque() == null) {
                    moto.setEstoque(0);
                }

                moto.setEstoque(
                        moto.getEstoque() + 1
                );

                motoRepository.save(moto);
            }

            vendaRepository.delete(venda);
        }

        return "redirect:/vendas";
    }

    private void carregarDadosFormulario(Model model) {

        model.addAttribute(
                "clientes",
                clienteRepository.findAll()
        );

        model.addAttribute(
                "motos",
                motoRepository.findAll()
        );
    }
}