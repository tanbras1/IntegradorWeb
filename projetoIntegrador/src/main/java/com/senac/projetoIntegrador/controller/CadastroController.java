/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.controller;

import com.senac.projetoIntegrador.model.Cadastro;
import com.senac.projetoIntegrador.repository.CadastroRepository;
import com.senac.projetoIntegrador.service.CadastroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @Autowired // Injeta automaticamente o repositório
    private CadastroRepository cadastroRepository;

    @GetMapping("/cadastrar")
    public String exibirFormulario(Model model) {
        model.addAttribute("cadastro", new Cadastro()); //Adiciona um objeto padrão ao modelo
        model.addAttribute("cadastros", cadastroService.listarTodos()); //Adiciona a lista de cadastros
        return "cadastro";
    }

    @PostMapping("/gravar")
    public String processarFormulario(@ModelAttribute Cadastro cadastro) {
        cadastroService.salvar(cadastro);
        return "redirect:/cadastro/cadastrar";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable int id, Model model) {
        model.addAttribute("cadastro", cadastroService.buscarPorId(id));
        return "/cadastro/cadastrar";
    }
    
    @PostMapping("/editar")
    public String editarCadastro(@ModelAttribute Cadastro cadastro, HttpSession session) {
        // Obtém o ID do usuário logado da sessão
        Integer idUsuarioLogado = (Integer) session.getAttribute("idUsuario");

        // Se o usuário não estiver autenticado, redireciona para login
        if (idUsuarioLogado == null) {
            return "redirect:/login";
        }

        // Buscar o cadastro original para preservar o tipo de usuário e senha criptografada
        Cadastro cadastroExistente = cadastroRepository.findById(idUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // **Garantir que apenas os dados permitidos sejam alterados**
        cadastroExistente.setNomeCliente(cadastro.getNomeCliente());
        cadastroExistente.setEmail(cadastro.getEmail());
        cadastroExistente.setEndereco(cadastro.getEndereco());
        cadastroExistente.setDataNascimento(cadastro.getDataNascimento());
        cadastroExistente.setTipoServico(cadastro.getTipoServico());

        //Não permitir alteração de usuário pelo cliente
        if (!cadastroExistente.getUsuario().equals("adm")) {
            cadastroExistente.setUsuario("cliente");
        }

        cadastroRepository.save(cadastroExistente);

        return "redirect:/pagina-emprestimo"; //Retorna à página após edição
    }

    @PostMapping("/excluir")
    public String excluir(@RequestParam int id) {
        cadastroService.excluir(id);
        return "redirect:/cadastro/cadastrar";
    }

}
