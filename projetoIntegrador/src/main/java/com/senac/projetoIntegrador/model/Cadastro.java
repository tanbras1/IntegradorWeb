package com.senac.projetoIntegrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Data
@Entity // Mapeia essa classe para o banco de dados
@Table(name = "cadastro") // Cria uma tabela chamada 'cadastro'
@Component // Permite ao Spring utilizar essa entidade no banco de dados
@DynamicUpdate
public class Cadastro {

    @Id // Identifica o id como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a forma de geração do ID (auto incremento)
    private int id;

    private String nomeCliente;
    private String usuario = "cliente";
    private String endereco;
    private String email;
    private LocalDate dataNascimento;
    private String tipoServico;
    
    @Column(nullable = false) // Garante que o campo não seja nulo
    private String senha; // Adicionado campo de senha
}
