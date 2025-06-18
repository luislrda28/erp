package br.com.erpsystem.erp_clientes.dto; // Ajuste o pacote

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@Data // Lombok: Gera getters, setters, toString, equals e hashCode
public class ClienteRequestDTO {

    // Não inclua o ID aqui, pois ele é gerado pelo banco para novas criações

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres (com ou sem máscara)")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    private String email;

    @Size(max = 20, message = "Telefone não pode exceder 20 caracteres")
    private String telefone;

    @Size(max = 255, message = "Endereço não pode exceder 255 caracteres")
    private String endereco;

    // Opcional: Se 'ativo' puder ser definido na criação, inclua-o
    // Se for sempre true por padrão, pode omitir aqui e definir na entidade
    private boolean ativo;
}