/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.controller;

import com.senac.projetoIntegrador.model.Simulacao;
import com.senac.projetoIntegrador.service.SimulacaoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @GetMapping("/simulacao")
    public String exibirFormulario(Model model) {
        model.addAttribute("simulacao", new Simulacao()); // Envia um objeto vazio para o formulário
        return "simulacao"; // Refere-se ao arquivo `cadastro.html` dentro de `templates/`
    }

    @PostMapping("/simulacao-gravar")
    public String processarFormulario(
            @RequestParam String nome_emprestimo,
            @RequestParam String tipo_emprestimo,
            @RequestParam double valor_emprestimo,
            @RequestParam int numero_parcelas,
            RedirectAttributes redirectAttributes) {

        double taxaJuros = tipo_emprestimo.equals("consignado") ? 1.5 : 3.5;
        double valorTotalComJuros = valor_emprestimo * Math.pow((1 + (taxaJuros / 100)), numero_parcelas);
        valorTotalComJuros = Math.round(valorTotalComJuros * 100.0) / 100.0;
        double valorParcelaComJuros = valorTotalComJuros / numero_parcelas;
        valorParcelaComJuros = Math.round(valorParcelaComJuros * 100.0) / 100.0;

        Simulacao simulacao = new Simulacao();
        simulacao.setNomeCliente(nome_emprestimo);
        simulacao.setTipo(tipo_emprestimo);
        simulacao.setValor(valorTotalComJuros);
        simulacao.setParcelas(numero_parcelas);
        simulacao.setJuros(taxaJuros);

        simulacaoService.salvar(simulacao); // Garante que não sobrepõe registros anteriores

        redirectAttributes.addFlashAttribute("simulacao", simulacao);
        redirectAttributes.addFlashAttribute("valorParcelaComJuros", valorParcelaComJuros);

        return "redirect:/novaSimulacao";
    }

    @GetMapping("/novaSimulacao")
    public String novaSimulacao(Model model) {
        Simulacao ultimaSimulacao = simulacaoService.buscarUltimaSimulacao(); // Busca a última simulação cadastrada

        if (ultimaSimulacao != null) {
            model.addAttribute("simulacao", ultimaSimulacao); // Garante que os valores sejam exibidos corretamente
        } else {
            model.addAttribute("simulacao", null); // Mantém a tela vazia se não houver simulação
        }

        return "simulacao";
    }

    @GetMapping("/simulacao-resultado")
    public String exibirSimulacao(Model model) {
        model.addAttribute("simulacao", new Simulacao()); // Objeto vazio ao iniciar a aplicação
        return "simulacao";
    }

    @GetMapping("/lista")
    @ResponseBody
    public List<Simulacao> listarSimulacoes() {
        return simulacaoService.listarTodos(); // Retorna todas as simulações salvas
    }

    @GetMapping("/detalhes/{id}")
    @ResponseBody
    public Simulacao exibirDetalhes(@PathVariable int id) {
        return simulacaoService.buscarPorId(id); // Retorna os detalhes da simulação específica
    }

    @PostMapping("/excluir")
    public String excluir(@RequestParam int id) {
        simulacaoService.excluir(id);
        return "redirect:/simulacao"; // Retorna para a página de simulação após exclusão
    }
}

