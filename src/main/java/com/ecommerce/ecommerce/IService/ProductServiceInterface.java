package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Dto.ProductRequest;
import com.ecommerce.ecommerce.Entities.Products;
import com.ecommerce.ecommerce.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ProductServiceInterface {
    public Page<Products> getProductByCategoryId(int cid, Pageable pageable);

    public void addProduct(MultipartFile file, ProductRequest product, User user, Integer cid);

}
