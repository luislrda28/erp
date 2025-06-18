package br.com.erpsystem.erp_produtos.controller; // Pacote do controlador de produtos

import br.com.erpsystem.erp_produtos.dto.ProdutoRequestDTO;
import br.com.erpsystem.erp_produtos.dto.ProdutoResponseDTO;
import br.com.erpsystem.erp_produtos.model.Produto;
import br.com.erpsystem.erp_produtos.service.ProdutoService; // Importa o ProdutoService
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos") // Novo caminho base para a API de produtos
public class ProdutoController {

    private final ProdutoService produtoService; // Injeta o ProdutoService

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // 1. Criar Produto (POST)
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produto = new Produto();
        // Mapeia DTO para Entidade
        produto.setSku(produtoDTO.getSku());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setAtivo(produtoDTO.isAtivo());

        Produto novoProduto = produtoService.salvar(produto);
        return new ResponseEntity<>(new ProdutoResponseDTO(novoProduto), HttpStatus.CREATED);
    }

    // 2. Listar Todos os Produtos (GET)
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        List<ProdutoResponseDTO> produtosDTO = produtos.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }

    // 3. Buscar Produto por ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(produto -> new ResponseEntity<>(new ProdutoResponseDTO(produto), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Atualizar Produto (PUT /{id})
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produto = new Produto(); // Crio um produto temporário para passar os dados para o service
        produto.setSku(produtoDTO.getSku());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setAtivo(produtoDTO.isAtivo());

        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return new ResponseEntity<>(new ProdutoResponseDTO(produtoAtualizado), HttpStatus.OK);
        } catch (RuntimeException e) { // Temporário: Tratar exceção de não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Deletar Produto (DELETE /{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) { // Temporário: Tratar exceção de não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Novos Endpoints para operações de Estoque
    @PutMapping("/{id}/adicionar-estoque")
    public ResponseEntity<ProdutoResponseDTO> adicionarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        try {
            Produto produtoAtualizado = produtoService.adicionarEstoque(id, quantidade);
            return new ResponseEntity<>(new ProdutoResponseDTO(produtoAtualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Aqui poderíamos adicionar um GlobalExceptionHandler para IllegalArgumentException
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/remover-estoque")
    public ResponseEntity<ProdutoResponseDTO> removerEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        try {
            Produto produtoAtualizado = produtoService.darBaixaEstoque(id, quantidade);
            return new ResponseEntity<>(new ProdutoResponseDTO(produtoAtualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Aqui poderíamos adicionar um GlobalExceptionHandler para IllegalArgumentException
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}