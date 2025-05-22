/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.service;

import com.senac.projetoIntegrador.model.Cadastro;
import com.senac.projetoIntegrador.repository.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class LoginService {
  @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private CadastroService cadastroService;

    public boolean validarUsuario(String nomeCliente, String senhaDigitada) {
        Cadastro cadastro = cadastroRepository.findByNomeCliente(nomeCliente); //Busca pelo nome

        if (cadastro != null) {
            return cadastroService.verificarSenha(senhaDigitada, cadastro.getSenha()); //Valida a senha corretamente!
        }

        return false; //Nome ou senha incorretos
    }
}
