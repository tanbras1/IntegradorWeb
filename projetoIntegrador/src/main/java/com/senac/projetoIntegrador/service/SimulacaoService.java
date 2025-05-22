/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador.service;

import com.senac.projetoIntegrador.model.Simulacao;
import com.senac.projetoIntegrador.repository.SimulacaoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class SimulacaoService {
    @Autowired
    private SimulacaoRepository simulacaoRepository;

    public Simulacao salvar(Simulacao simulacao) {
        return simulacaoRepository.save(simulacao);
    }

    public List<Simulacao> listarTodos() {
        return simulacaoRepository.findAll();
    }

    public Simulacao buscarPorId(int id) {
        return simulacaoRepository.findById(id).orElse(null);
    }

    public void excluir(int id) {
        if (simulacaoRepository.existsById(id)) { // Verifica se o filme existe antes de excluir
            simulacaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Filme com ID " + id + " não encontrado."); //Evita erro silencioso
        }
    }
    
    public Simulacao buscarUltimaSimulacao() {
    return simulacaoRepository.findTopByOrderByIdDesc(); //Obtém a última simulação cadastrada
}


}
