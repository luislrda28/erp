package br.com.erpsystem.erp_produtos.dto; // Pacote dos DTOs de produto

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {

    // Não inclua o ID aqui, é para criação/atualização
    @NotBlank(message = "SKU é obrigatório")
    @Size(max = 50, message = "SKU não pode exceder 50 caracteres")
    private String sku;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 200, message = "Nome não pode exceder 200 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição não pode exceder 500 caracteres")
    private String descricao;

    @NotNull(message = "Preço de custo é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Preço de custo não pode ser negativo")
    private BigDecimal precoCusto;

    @NotNull(message = "Preço de venda é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Preço de venda deve ser maior que zero")
    private BigDecimal precoVenda;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "Quantidade em estoque não pode ser negativa")
    private Integer quantidadeEstoque;

    private boolean ativo; // Pode ser definido na requisição
}