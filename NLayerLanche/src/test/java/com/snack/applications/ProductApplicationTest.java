package com.snack.applications;

import com.snack.UtilsTest;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductApplicationTest {
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
        productApplication = new ProductApplication(productRepository, productService);
        product1 = new Product(1, "Hot Dog", 8.4f, UtilsTest.GlobalPath + "hotdog.jpg");
        product2 = new Product(2, "coxinha", 10.4f, UtilsTest.GlobalPath + "coxinha.jpg");

        productApplication.append( product1);
        productApplication.append( product2);
    }

    @Test
    public void verificarSeTodosOsProdutosSaoListados() {
        assertNotNull(productApplication.getAll());
    }

    @Test
    public void validarProdutoObtidoPorId(){
        Product product2 = productApplication.getById(2);
        assertEquals("coxinha", product2.getDescription());
        assertEquals(10.4f, product2.getPrice());
        assertEquals(UtilsTest.GlobalPath + "2.jpg", product2.getImage());
    }

    @Test
    public void verificarSeProdutoDaErroSeIdInexistente() {
        assertThrows(NoSuchElementException.class, ()->{
            productApplication.getById(9);
        });
    }

    @Test
    public void validarSeProdutoExistePorIdValido() {
        boolean product1 = productApplication.exists(2);
        assertTrue(product1);
    }

    @Test
    public void validarSeProdutoInexistentePorIdInvalido() {
        boolean product6 = productApplication.exists(6);
        assertFalse(product6);
    }

    @Test
    public void verificarSeProdutoEImagemAdicionadoESalvoCorretamente() {
        Product product3 = new Product(3, "macarrao", 7.4f, UtilsTest.GlobalPath + "macarrao.jpg");
        productApplication.append(product3);

        assertEquals("macarrao", product3.getDescription());
        assertEquals(UtilsTest.GlobalPath + "3.jpg", product3.getImage());
    }

    @Test
    public void verificarRemocaoDoProdutoESuaImagem() {
        productApplication.remove(2);
        assertFalse(productApplication.exists(2));
    }

    @Test
    public void testarSeSistemaContinuaOMesmoAposRemoverProdutoComIdInexistente() {
        assertThrows(NoSuchElementException.class, () ->{
            productApplication.remove(6);
        });

    }

    @Test
    public void testarAtualizacaoDeProdutoJuntoComSuaImagem(){
        Product productAAlterar = new Product(2, "Bacon", 55, UtilsTest.GlobalPath + "bacon.jpg");

        productApplication.update(2, productAAlterar);

        Product produtoAlterado = productApplication.getById(2);

        File f = new File(UtilsTest.GlobalPath + "2.jpg");

        assertEquals(product2, produtoAlterado);
        Assertions.assertTrue(f.exists());
    }

}
