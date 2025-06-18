package br.com.erpsystem.erp_clientes.repository; // ATENÇÃO: Ajuste o pacote base para o seu projeto

import br.com.erpsystem.erp_clientes.model.Cliente; // Importa a classe Cliente
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository
import org.springframework.stereotype.Repository; // Opcional, mas boa prática

@Repository // Indica que esta interface é um componente Spring de persistência
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Spring Data JPA magic!
    // Ao estender JpaRepository, você automaticamente ganha métodos CRUD:
    // save(), findById(), findAll(), delete(), etc.

    // Você também pode adicionar métodos personalizados aqui, como:
    // Cliente findByCpf(String cpf);
    // List<Cliente> findByNomeContainingIgnoreCase(String nome);
    // boolean existsByCpf(String cpf);
}