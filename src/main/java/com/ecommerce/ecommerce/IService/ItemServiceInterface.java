package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Dto.ItemRequest;
import com.ecommerce.ecommerce.Entities.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public interface ItemServiceInterface {
    public void addItem(MultipartFile file, ItemRequest itemRequest, User user, Integer pid);
}
