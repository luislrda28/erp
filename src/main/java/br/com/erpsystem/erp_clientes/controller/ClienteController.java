package br.com.erpsystem.erp_clientes.controller;

import br.com.erpsystem.erp_clientes.model.Cliente;
import br.com.erpsystem.erp_clientes.service.ClienteService; // Importa o ClienteService
import br.com.erpsystem.erp_clientes.dto.ClienteRequestDTO;
import br.com.erpsystem.erp_clientes.dto.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired; // Não será mais usado, mas pode manter por enquanto
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService; // Injetar ClienteService

    // Injeção de dependência via construtor
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 1. Endpoint para criar um novo Cliente (CREATE)
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setAtivo(clienteDTO.isAtivo());

        Cliente novoCliente = clienteService.salvar(cliente); // Chama o Service
        return new ResponseEntity<>(new ClienteResponseDTO(novoCliente), HttpStatus.CREATED);
    }

    // 2. Endpoint para listar todos os Clientes (READ ALL)
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos(); // Chama o Service
        List<ClienteResponseDTO> clientesDTO = clientes.stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientesDTO, HttpStatus.OK);
    }

    // 3. Endpoint para buscar um Cliente por ID (READ ONE)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id); // Chama o Service
        return cliente.map(value -> new ResponseEntity<>(new ClienteResponseDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Endpoint para atualizar um Cliente existente (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        Cliente cliente = new Cliente(); // Poderíamos buscar o cliente e atualizar o objeto existente com os dados do DTO
        cliente.setId(id); // Importante para o service saber qual atualizar
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setAtivo(clienteDTO.isAtivo());

        try {
            Cliente clienteAtualizado = clienteService.atualizar(id, cliente); // Chama o Service
            return new ResponseEntity<>(new ClienteResponseDTO(clienteAtualizado), HttpStatus.OK);
        } catch (RuntimeException e) { // Trate a exceção de "não encontrado"
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Endpoint para deletar um Cliente (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletar(id); // Chama o Service
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) { // Trate a exceção de "não encontrado"
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}