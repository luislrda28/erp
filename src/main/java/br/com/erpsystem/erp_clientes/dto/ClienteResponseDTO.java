package br.com.erpsystem.erp_clientes.dto; // Ajuste o pacote

import lombok.Data;
import br.com.erpsystem.erp_clientes.model.Cliente; // Importa a entidade Cliente

@Data // Lombok: Gera getters, setters, toString, equals e hashCode
public class ClienteResponseDTO {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    // Construtor que recebe um objeto Cliente (Entidade) e mapeia para o DTO
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.endereco = cliente.getEndereco();
        this.ativo = cliente.isAtivo();
    }

    // Se você quiser um construtor sem argumentos para outros casos (ex: testes ou frameworks)
    public ClienteResponseDTO() {
    }
}