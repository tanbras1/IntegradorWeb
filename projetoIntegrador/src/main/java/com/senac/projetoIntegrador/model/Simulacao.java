package com.senac.projetoIntegrador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity //Mapeia essa classe livro para MSQl e vice versa
@Table(name = "simulacao")// Cria uma tabela de filme
@Component //Permite o spring utilizar o banco de dados
public class Simulacao {

    @Id // identifica o id como chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//DEFINE A FORMA QUE O ID SERÁ IMPLEMENTADO 1234...
    private int id;
    private String nomeCliente;
    private String tipo;
    private double juros;
    private double valor;
    private int parcelas;
    
    @ManyToOne//Pode ter varios comentarios em um livro
    @JoinColumn(name="cadastro_id")//chave estrangeira
    @JsonBackReference//Para evitar o loop infinito
    private Cadastro cadastro;

}
