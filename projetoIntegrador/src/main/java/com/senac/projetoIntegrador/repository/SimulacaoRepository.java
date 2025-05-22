/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senac.projetoIntegrador.repository;

import com.senac.projetoIntegrador.model.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface SimulacaoRepository extends JpaRepository<Simulacao, Integer>{

   
    Simulacao findTopByOrderByIdDesc();
}
