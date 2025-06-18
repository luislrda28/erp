package br.com.erpsystem.erp_produtos.dto; // Pacote dos DTOs de produto

import lombok.Data;
import br.com.erpsystem.erp_produtos.model.Produto; // Importa a entidade Produto

import java.math.BigDecimal;

@Data
public class ProdutoResponseDTO {

    private Long id;
    private String sku;
    private String nome;
    private String descricao;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private Integer quantidadeEstoque;
    private boolean ativo;

    // Construtor para converter de entidade Produto para DTO
    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.sku = produto.getSku();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoCusto = produto.getPrecoCusto();
        this.precoVenda = produto.getPrecoVenda();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.ativo = produto.isAtivo();
    }

    public ProdutoResponseDTO() { // Construtor padrão
    }
}