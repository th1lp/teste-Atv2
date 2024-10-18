package com.snack.repositories;



import com.snack.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        product1 = new Product(1, "Hot Dog", 10.4f, ""); // 0x0003
        product2 = new Product(2, "coxinha", 10.4f, ""); // 0x0005
        product3 = new Product(3, "macarrao", 10.4f, "");// 0x0002

        productRepository.append(product1);
        productRepository.append(product2);
        productRepository.append(product3);
    }

    @Test
    public void verificarSeOProdutoEAdicionadoCorretamente() {
        Product product4 = new Product(4, "Camarao", 9.9f, "");

        productRepository.append(product4);

        Product productId4 = productRepository.getById(4);
        assertNotNull(productId4);
    }

    @Test
    public void testarSeProdutoERecuperadoPeloSeuId(){
        Product productId2 = productRepository.getById(2);
        assertNotNull(productId2);
    }

    @Test
    public void verificarSeProdutoExisteNoRepositorio(){
        assertTrue(productRepository.exists(1));
    }

    @Test
    public void verificarSeProdutoFoiRemovido(){
        productRepository.remove(1);
        assertFalse(productRepository.exists(1));
    }

    @Test
    public void verificarSeProdutoFoiAtualizado(){
        Product productAAtualizar = new Product(3, "Bacon", 55, "");

        productRepository.update(1, productAAtualizar);

        Product produtoAtualizado = productRepository.getById(1);

        assertEquals(product1, produtoAtualizado);
    }

    @Test
    public void verificarSeProdutosArmazenadosSaoRecuperados(){
        productRepository.getAll();
        assertNotNull(productRepository);
    }

    @Test
    public void verificarComportamentoAoTentarRemoverProdutoInexistente() {
        productRepository.remove(4);
        Assertions.assertFalse(productRepository.exists(4));
    }

    @Test
    public void verificarComportamentoAoAtualizarProdutoInexistente() {
        Product productAAtualizar = new Product(1, "Bacon", 55, "");

        Assertions.assertThrows(NoSuchElementException.class,() -> productRepository.update(4, productAAtualizar));
    }

    @Test
    public void verificarSeAceitaAdicaoDeProdutosComIdsDuplicados(){
        Product product4 = new Product(4, "Camarao", 9.9f, "");
        Product product5 = new Product(4, "pipoca", 4.9f, "");

        productRepository.append(product4);
        productRepository.append(product5);
        Product productId4 = productRepository.getById(4);
        Product productId5 = productRepository.getById(4);
        assertNotNull(productId4);
        assertNotNull(productId5);
    }

    @Test
    public void verificarSeAListaEVaziaQuandoIniciada(){
        ProductRepository productRepository = new ProductRepository();
        assertEquals(0,productRepository.getAll().size());
    }

}
