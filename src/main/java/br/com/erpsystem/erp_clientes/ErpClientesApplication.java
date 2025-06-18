package br.com.erpsystem.erp_clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // Importe esta anotação

@SpringBootApplication
@EntityScan({
		"br.com.erpsystem.erp_clientes.model",
		"br.com.erpsystem.erp_produtos.model"
})
@ComponentScan({
		"br.com.erpsystem.erp_clientes",
		"br.com.erpsystem.erp_produtos",
		"br.com.erpsystem.erp_clientes.security"
})
@EnableJpaRepositories({
		"br.com.erpsystem.erp_clientes.repository", // Seu pacote de repositórios de cliente
		"br.com.erpsystem.erp_produtos.repository"  // SEU NOVO PACOTE DE REPOSITÓRIOS DE PRODUTO
})
public class ErpClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpClientesApplication.class, args);
	}

}