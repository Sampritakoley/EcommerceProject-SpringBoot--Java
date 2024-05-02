package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Entities.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface BrandServiceInterface {
    public void addBrand(MultipartFile file, String descriptions, String brand_name, User user, Integer cid);
}
