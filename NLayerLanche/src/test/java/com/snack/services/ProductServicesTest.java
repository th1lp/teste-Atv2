package com.snack.services;

import com.snack.UtilsTest;
import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServicesTest {
    private static ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productService = new ProductService();
        product1 = new Product(1, "Hot Dog", 8.4f, UtilsTest.GlobalPath + "hotdog.jpg"); // 0x0003
        product2 = new Product(2, "coxinha", 10.4f, UtilsTest.GlobalPath + "coxinha.jpg"); // 0x0005

        productService.save(product1);
        productService.save(product2);
    }

    @Test
    public void validarSeProdutoESalvoComImagem() {
        Product product4 = new Product(4, "macarrao", 9.9f, UtilsTest.GlobalPath + "macarrao.jpg");

        assertTrue(productService.save(product4));
    }

    @Test
    public void testarSeProdutoESalvoSemImagemValida(){
        Product product5 = new Product(5, "Uva", 3.9f, UtilsTest.GlobalPath + "uva.jpg");
        boolean isSaved = productService.save(product5);
        assertFalse(isSaved);
    }

    @Test
    public void testRemoverProdutoExistente() {
        Path path = Paths.get(productService.getImagePathById(product2.getId()));
        productService.remove(product2.getId());
        assertFalse(Files.exists(path));
    }

    @Test
    public void TestarAtualizarproduto() {
        product1.setDescription("Pipoca");
        productService.update(product1);
        assertEquals("Pipoca",product1.getDescription());
    }

    @Test
    public void TesteObterCaminhoImgPorId() {
        Path path = Paths.get(productService.getImagePathById(4));
        assertTrue(Files.exists(path));
    }

}
