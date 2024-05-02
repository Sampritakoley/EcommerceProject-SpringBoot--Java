package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Dto.ItemRequest;
import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.Products;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.IService.ItemServiceInterface;
import com.ecommerce.ecommerce.Repository.BrandRepository;
import com.ecommerce.ecommerce.Repository.ItemRepository;
import com.ecommerce.ecommerce.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
@Service
public class ItemService implements ItemServiceInterface {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addItem(MultipartFile file, ItemRequest itemRequest, User user, Integer pid)
    {
        try
        {
            ModelMapper modelMapper = new ModelMapper();
            Items newItem=modelMapper.map(itemRequest, Items.class);
            if(file.isEmpty()){
                System.out.println("File is empty");
            }else{
                newItem.setImage(file.getOriginalFilename());
                File saveFile=new ClassPathResource("/static/image").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File is uploaded successfully");
            }
            newItem.setCreated_by(user.getFirst_name()+" "+user.getLast_name());
            newItem.setCreated_at(new java.sql.Timestamp(new Date().getTime()));
            if(itemRequest.getQuantity()>0){
                newItem.setAvailable(true);
            }
            Brands brands=brandRepository.getBrandByName(itemRequest.getBrand_name());
            Products products=productRepository.getProductById(pid);
            newItem.setBrand(brands);
            newItem.setProducts(products);
            itemRepository.save(newItem);
        }catch(Exception e){

        }

    }
}
