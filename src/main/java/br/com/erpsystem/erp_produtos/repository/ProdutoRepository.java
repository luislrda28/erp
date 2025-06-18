package br.com.erpsystem.erp_produtos.repository; // Pacote do repositório de produtos

import br.com.erpsystem.erp_produtos.model.Produto; // Importa a entidade Produto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Métodos de CRUD básicos já vêm do JpaRepository

    // Podemos adicionar métodos de busca personalizados, por exemplo:
    Optional<Produto> findBySku(String sku); // Busca um produto pelo SKU
    boolean existsBySku(String sku); // Verifica se um SKU já existe
}