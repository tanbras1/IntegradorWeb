/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.controller;

import com.senac.projetoIntegrador.model.Cadastro;
import com.senac.projetoIntegrador.model.Simulacao;
import com.senac.projetoIntegrador.repository.CadastroRepository;
import com.senac.projetoIntegrador.service.SimulacaoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/pagina-emprestimo")
public class EmprestimoController {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private SimulacaoService simulacaoService;

    @PostMapping("/gravar")
    public String salvarSimulacaoCliente(
            @RequestParam String nome_emprestimo,
            @RequestParam String tipo_emprestimo,
            @RequestParam double valor_emprestimo,
            @RequestParam int numero_parcelas,
            RedirectAttributes redirectAttributes,
            HttpSession session) { //Obtém a sessão do usuário

        double taxaJuros = tipo_emprestimo.equals("consignado") ? 1.5 : 3.5;
        double valorTotalComJuros = valor_emprestimo * Math.pow((1 + (taxaJuros / 100)), numero_parcelas);
        valorTotalComJuros = Math.round(valorTotalComJuros * 100.0) / 100.0;
        double valorParcelaComJuros = valorTotalComJuros / numero_parcelas;
        valorParcelaComJuros = Math.round(valorParcelaComJuros * 100.0) / 100.0;

        //Recupera o ID do usuário logado na sessão
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        if (idUsuario == null) {
            return "redirect:/login"; // Se não tiver usuário logado, redireciona para login
        }

        // Busca o cadastro do usuário no banco de dados
        Cadastro cadastro = cadastroRepository.findById(idUsuario).orElse(null);
        if (cadastro == null) {
            return "redirect:/login"; //Se não encontrar, volta para login
        }

        //simulação e associa ao cadastro
        Simulacao simulacao = new Simulacao();
        simulacao.setNomeCliente(nome_emprestimo);
        simulacao.setTipo(tipo_emprestimo);
        simulacao.setValor(valorTotalComJuros);
        simulacao.setParcelas(numero_parcelas);
        simulacao.setJuros(taxaJuros);
        simulacao.setCadastro(cadastro); //Associa ao usuário

        simulacaoService.salvar(simulacao); //Salva com chave estrangeira

        redirectAttributes.addFlashAttribute("simulacao", simulacao);
        redirectAttributes.addFlashAttribute("valorParcelaComJuros", valorParcelaComJuros);

        return "redirect:/pagina-emprestimo";
    }

    @GetMapping
    public String exibirPaginaEmprestimo(HttpSession session, Model model,
            @RequestParam(required = false) Integer idBusca,
            @RequestParam(required = false) String nomeBusca) {
        Integer idUsuarioLogado = (Integer) session.getAttribute("idUsuario");

        if (idUsuarioLogado == null) {
            return "redirect:/login";
        }

        Cadastro cadastro = cadastroRepository.findById(idUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("cadastro", cadastro);
        model.addAttribute("usuario", cadastro.getUsuario());

        List<Cadastro> cadastros = cadastroRepository.findAll(); //Lista todos inicialmente

        // Se buscar por ID, carregar o usuário correspondente
        if (idBusca != null) {
            cadastros = cadastroRepository.findById(idBusca)
                    .map(List::of)
                    .orElse(List.of());
            cadastro = cadastroRepository.findById(idBusca).orElse(null);
        } // Se buscar por nome, carregar o primeiro usuário encontrado na edição
        else if (nomeBusca != null && !nomeBusca.isEmpty()) {
            cadastros = cadastroRepository.findByNomeClienteContainingIgnoreCase(nomeBusca);
            if (!cadastros.isEmpty()) {
                cadastro = cadastros.get(0); //Carrega o primeiro resultado no formulário
            } else {
                cadastro = null; // Caso não encontre ninguém
            }
        }

        model.addAttribute("cadastro", cadastro);
        model.addAttribute("cadastros", cadastros);
        return "emprestimo";
    }

    @PostMapping("/editar")
    public String editarCadastro(@ModelAttribute Cadastro cadastro) {
        cadastroRepository.save(cadastro); //Salva alterações
        return "redirect:/pagina-emprestimo"; // Retorna à listagem
    }

    @PostMapping("/excluir")
    public String excluirCadastro(@RequestParam Integer id) {
        cadastroRepository.deleteById(id); //Exclui o cadastro
        return "redirect:/pagina-emprestimo";
    }

}
