package com.erdem.readingisgood.service;

import com.erdem.readingisgood.model.ActionLog;
import com.erdem.readingisgood.model.Product;
import com.erdem.readingisgood.repository.ProductRepository;
import com.erdem.readingisgood.rest.exception.ResourceNotFoundException;
import com.erdem.readingisgood.rest.model.request.CreateOrUpdateProductRequest;
import com.erdem.readingisgood.rest.model.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ActionLogService actionLogService;
    private final DozerBeanMapper dozerBeanMapper;

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream().map(product -> dozerBeanMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

    public void saveWithRequest(CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        this.save(dozerBeanMapper.map(createOrUpdateProductRequest, Product.class));
    }

    public void updateWithRequest(String id, CreateOrUpdateProductRequest createOrUpdateProductRequest) throws ResourceNotFoundException {
        if (productRepository.existsById(id)) {
            Product updatedProduct = dozerBeanMapper.map(createOrUpdateProductRequest, Product.class);
            updatedProduct.setId(id);
            this.update(updatedProduct);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Product findProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id: " + id + " not found."));
    }

    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        actionLogService.save(savedProduct, ActionLog.ActionLogTypeEnum.INSERT, "Product object inserted");
        return savedProduct;
    }

    public Product update(Product product) {
        Product updatedProduct = productRepository.save(product);
        actionLogService.save(updatedProduct, ActionLog.ActionLogTypeEnum.UPDATE, "Product object update");
        return updatedProduct;
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
        actionLogService.save(id, ActionLog.ActionLogTypeEnum.DELETE, "Product object deleted id = " + id);
    }
}
