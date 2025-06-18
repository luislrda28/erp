package br.com.erpsystem.erp_clientes.service; // Ajuste o pacote

import br.com.erpsystem.erp_clientes.model.Cliente;
import br.com.erpsystem.erp_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Importar @Service

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um componente de serviço Spring
public class ClienteService {

    private final ClienteRepository clienteRepository; // Usar 'final' e injetar via construtor (boa prática)

    // Injeção de dependência via construtor (preferível ao @Autowired no atributo)
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Método para criar/salvar um cliente
    public Cliente salvar(Cliente cliente) {
        // Futuramente, regras de negócio mais complexas iriam aqui antes de salvar
        // Ex: Verificar se CPF já existe no DB (além da validação de formato)
        // Ex: Aplicar alguma transformação nos dados
        return clienteRepository.save(cliente);
    }

    // Método para listar todos os clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Método para buscar cliente por ID
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Método para atualizar cliente
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        // Aqui adicionamos a lógica para buscar e depois atualizar,
        // garantindo que não estamos criando um novo cliente se o ID não existe
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setCpf(clienteAtualizado.getCpf());
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setEndereco(clienteAtualizado.getEndereco());
            clienteExistente.setAtivo(clienteAtualizado.isAtivo());
            return clienteRepository.save(clienteExistente);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id)); // Futuramente, uma exceção customizada
    }

    // Método para deletar cliente
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para exclusão com ID: " + id); // Futuramente, uma exceção customizada
        }
        clienteRepository.deleteById(id);
    }
}