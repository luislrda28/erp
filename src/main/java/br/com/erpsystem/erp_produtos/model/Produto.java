package br.com.erpsystem.erp_produtos.model; // ATENÇÃO: Novo pacote para produtos!

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero; // Importe para valores não negativos
import jakarta.validation.constraints.DecimalMin;    // Importe para valores mínimos decimais

import java.math.BigDecimal; // Usaremos BigDecimal para valores monetários

@Entity
@Table(name = "produtos") // Mapeia para a tabela 'produtos'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "SKU é obrigatório")
    @Column(nullable = false, unique = true, length = 50)
    private String sku; // Stock Keeping Unit - Código único do produto

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, length = 200)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @NotNull(message = "Preço de custo é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Preço de custo não pode ser negativo")
    @Column(nullable = false, precision = 10, scale = 2) // Precision: total de dígitos, Scale: dígitos após a vírgula
    private BigDecimal precoCusto; // Usar BigDecimal para precisão monetária

    @NotNull(message = "Preço de venda é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Preço de venda deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda; // Usar BigDecimal para precisão monetária

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "Quantidade em estoque não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque; // Integer para quantidade

    @Column(nullable = false)
    private boolean ativo; // Indica se o produto está ativo/disponível

    // Métodos de negócio para controle de estoque
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade < 0) {
            throw new IllegalArgumentException("Quantidade a adicionar deve ser positiva.");
        }
        this.quantidadeEstoque += quantidade;
        System.out.println("Adicionado " + quantidade + " ao estoque do produto " + this.nome);
    }

    public void removerEstoque(Integer quantidade) {
        if (quantidade == null || quantidade < 0) {
            throw new IllegalArgumentException("Quantidade a remover deve ser positiva.");
        }
        if (this.quantidadeEstoque < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para remover " + quantidade + " unidades do produto " + this.nome);
        }
        this.quantidadeEstoque -= quantidade;
        System.out.println("Removido " + quantidade + " do estoque do produto " + this.nome);
    }
}