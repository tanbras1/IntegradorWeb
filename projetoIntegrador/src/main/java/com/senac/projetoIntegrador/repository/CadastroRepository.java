/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senac.projetoIntegrador.repository;

import com.senac.projetoIntegrador.model.Cadastro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface CadastroRepository extends JpaRepository<Cadastro, Integer>{
      // Busca um cadastro pelo e-mail
    Cadastro findByEmail(String email);
    Cadastro findByNomeCliente(String nomeCliente);
    //Busca por nome parcial (case-insensitive)
    List<Cadastro> findByNomeClienteContainingIgnoreCase(String nomeCliente);
    
}
