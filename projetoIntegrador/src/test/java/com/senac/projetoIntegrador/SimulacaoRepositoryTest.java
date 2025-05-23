/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.projetoIntegrador;


import com.senac.projetoIntegrador.model.Simulacao;
import com.senac.projetoIntegrador.repository.SimulacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class SimulacaoRepositoryTest {
    
    @Autowired
    private SimulacaoRepository simulacaoRepository;

    @Test
    void testBuscarQuantidadeParcelasPorId() {
        // Busca o empréstimo com ID 3
        Simulacao simulacao = simulacaoRepository.findById(3).orElse(null);
        
        // Valida se o empréstimo existe e se a quantidade de parcelas está correta
        assertThat(simulacao).isNotNull();
        assertThat(simulacao.getParcelas()).isGreaterThan(0);
    }
}
