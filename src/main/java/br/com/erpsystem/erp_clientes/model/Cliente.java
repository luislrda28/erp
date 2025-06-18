package br.com.erpsystem.erp_clientes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank; // Nova importação
import jakarta.validation.constraints.Size;   // Nova importação
import jakarta.validation.constraints.Email;   // Nova importação
import org.hibernate.validator.constraints.br.CPF; // Nova importação para validação de CPF BR

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CPF é obrigatório") // Não pode ser nulo e nem vazio/só espaços
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres (com ou sem máscara)") // Tamanho para CPF
    @CPF(message = "CPF inválido") // Validação de CPF para o Brasil
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Email(message = "Email inválido") // Validação de formato de e-mail
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    @Column(length = 100)
    private String email;

    @Size(max = 20, message = "Telefone não pode exceder 20 caracteres")
    @Column(length = 20)
    private String telefone;

    @Size(max = 255, message = "Endereço não pode exceder 255 caracteres")
    @Column(length = 255)
    private String endereco;

    @Column(nullable = false)
    private boolean ativo;

    public void inativar() {
        this.ativo = false;
        System.out.println("Cliente " + this.nome + " inativado.");
    }

    public void ativar() {
        this.ativo = true;
        System.out.println("Cliente " + this.nome + " ativado.");
    }
}