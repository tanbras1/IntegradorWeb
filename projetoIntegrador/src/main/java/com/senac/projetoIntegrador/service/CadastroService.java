/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.service;

import com.senac.projetoIntegrador.model.Cadastro;
import com.senac.projetoIntegrador.repository.CadastroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author User
 */
@Service
public class CadastroService {

    @Autowired
    private CadastroRepository cadastroRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Cadastro salvar(Cadastro cadastro) {
        // Criptografando a senha antes de salvar no banco
        cadastro.setSenha(passwordEncoder.encode(cadastro.getSenha()));
        return cadastroRepository.save(cadastro);
    }
    
    public Cadastro editar(Cadastro cadastro, String tipoUsuarioLogado) {
    Cadastro usuarioExistente = cadastroRepository.findById(cadastro.getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    //Impede que usuários comuns alterem o tipo de usuário
    if (!tipoUsuarioLogado.equals("adm")) {
        cadastro.setUsuario(usuarioExistente.getUsuario()); //Mantém o valor original
    }

    // Preserva senha antiga se não for enviada na requisição
    if (cadastro.getSenha() == null || cadastro.getSenha().isEmpty()) {
        cadastro.setSenha(usuarioExistente.getSenha());
    } else {
        cadastro.setSenha(passwordEncoder.encode(cadastro.getSenha()));
    }

    return cadastroRepository.save(cadastro);
}


    public boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return passwordEncoder.matches(senhaDigitada, senhaArmazenada);
    }

    public List<Cadastro> listarTodos() {
        return cadastroRepository.findAll();
    }

    public Cadastro buscarPorId(int id) {
        return cadastroRepository.findById(id).orElse(null);
    }

    public void excluir(int id) {
        if (cadastroRepository.existsById(id)) { // Verifica se o filme existe antes de excluir
            cadastroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Filme com ID " + id + " não encontrado."); // Evita erro silencioso
        }
    }
}
