package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ResponseEntity<Iterable<Product>> getAllProduct(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Optional<Product> productOptional = productService.findByID(id);
        return productOptional.map(product -> new ResponseEntity<>(product,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable("id") Long id, @RequestBody Product product){
        Optional<Product> productOptional = productService.findByID(id);
        return productOptional.map(product1 -> {
            product.setId(product1.getId());
            if (product.getName().equalsIgnoreCase("")){
            product.setName(product1.getName());}
            return new ResponseEntity<>(productService.save(product),HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional = productService.findByID(id);
        return productOptional.map(product -> {
            productService.remove(id);
            return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
