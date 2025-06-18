package br.com.erpsystem.erp_produtos.service; // Pacote do serviço de produtos

import br.com.erpsystem.erp_produtos.model.Produto;
import br.com.erpsystem.erp_produtos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um componente de serviço Spring
public class ProdutoService {

    private final ProdutoRepository produtoRepository; // Injeção de dependência via construtor

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método para salvar um produto (criação e atualização básica)
    public Produto salvar(Produto produto) {
        // Regra de negócio simples: SKU deve ser único
        // Já temos a restrição UNIQUE no DB e validação no DTO,
        // mas é bom ter uma checagem aqui também para casos de uso interno ou camadas diferentes
        if (produto.getId() == null && produtoRepository.existsBySku(produto.getSku())) {
            throw new IllegalArgumentException("SKU já cadastrado para outro produto.");
        }
        return produtoRepository.save(produto);
    }

    // Método para listar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Método para buscar produto por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Método para atualizar um produto existente
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    // Ao atualizar, o SKU só pode ser o mesmo ou um SKU que ainda não existe
                    if (!produtoExistente.getSku().equals(produtoAtualizado.getSku()) && produtoRepository.existsBySku(produtoAtualizado.getSku())) {
                        throw new IllegalArgumentException("SKU já cadastrado para outro produto.");
                    }
                    produtoExistente.setSku(produtoAtualizado.getSku());
                    produtoExistente.setNome(produtoAtualizado.getNome());
                    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                    produtoExistente.setPrecoCusto(produtoAtualizado.getPrecoCusto());
                    produtoExistente.setPrecoVenda(produtoAtualizado.getPrecoVenda());
                    produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
                    produtoExistente.setAtivo(produtoAtualizado.isAtivo());
                    return produtoRepository.save(produtoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id)); // Exceção temporária
    }

    // Método para deletar produto por ID
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado para exclusão com ID: " + id); // Exceção temporária
        }
        produtoRepository.deleteById(id);
    }

    // Métodos de negócio específicos de estoque (chamando os métodos da entidade Produto)
    public Produto darBaixaEstoque(Long id, Integer quantidade) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.removerEstoque(quantidade); // Chama o método da entidade
                    return produtoRepository.save(produtoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado para dar baixa no estoque com ID: " + id));
    }

    public Produto adicionarEstoque(Long id, Integer quantidade) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.adicionarEstoque(quantidade); // Chama o método da entidade
                    return produtoRepository.save(produtoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado para adicionar estoque com ID: " + id));
    }
}