/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.controller;

import com.senac.projetoIntegrador.model.Cadastro;
import com.senac.projetoIntegrador.repository.CadastroRepository;
import com.senac.projetoIntegrador.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CadastroRepository cadastroRepository;

    @GetMapping
    public String exibirLogin() {
        return "login"; // Isso precisa corresponder ao nome correto do arquivo login.html
    }

 @PostMapping
    public String processarLogin(@RequestParam String nomeCliente, @RequestParam String senha,
            HttpSession session, Model model) {
        Cadastro cadastro = cadastroRepository.findByNomeCliente(nomeCliente);

        if (cadastro != null && loginService.validarUsuario(nomeCliente, senha)) {
            //Salva ID e tipo na sessão
            session.setAttribute("idUsuario", cadastro.getId());
            session.setAttribute("usuario", cadastro.getUsuario());

            //Redireciona baseado no tipo de usuário
            return switch (cadastro.getUsuario().toLowerCase()) {
                case "cliente" -> "redirect:/pagina-emprestimo";
                case "funcionario" -> "redirect:/pagina-emprestimo";
                case "adm" -> "redirect:/pagina-emprestimo";
                default -> "login";
            }; //Se houver erro, mantém na tela de login
        } else {
            model.addAttribute("erro", "Nome ou senha inválidos. Tente novamente.");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //Limpa todos os dados da sessão
        return "redirect:/login"; //Redireciona para a tela de login
    }

}
