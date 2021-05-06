package com.erdem.readingisgood.rest.controller;

import com.erdem.readingisgood.rest.exception.ResourceNotFoundException;
import com.erdem.readingisgood.rest.model.common.Response;
import com.erdem.readingisgood.rest.model.request.CreateOrUpdateProductRequest;
import com.erdem.readingisgood.rest.model.response.ProductResponse;
import com.erdem.readingisgood.rest.util.ResponseDispatcher;
import com.erdem.readingisgood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Response<List<ProductResponse>> getProducts() {
        return ResponseDispatcher.successResponse(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping
    public Response<Void> createProduct(@RequestBody @Valid CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        productService.saveWithRequest(createOrUpdateProductRequest);
        return ResponseDispatcher.successResponse(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public Response<Void> updateProduct(@PathVariable String id, @RequestBody @Valid CreateOrUpdateProductRequest createOrUpdateProductRequest) throws ResourceNotFoundException {
        productService.updateWithRequest(id, createOrUpdateProductRequest);
        return ResponseDispatcher.successResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteProduct(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseDispatcher.successResponse(HttpStatus.OK);
    }
}
