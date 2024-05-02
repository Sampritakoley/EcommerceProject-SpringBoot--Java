package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Dto.ProductRequest;
import com.ecommerce.ecommerce.Entities.Products;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.IService.ProductServiceInterface;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import com.ecommerce.ecommerce.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Products> getProductByCategoryId(int cid, Pageable pageable){

        return productRepository.getProductByCategoryId(cid,pageable);
    }

    public void addProduct(MultipartFile file, ProductRequest product, User user,Integer cid)
    {
        try
        {
            ModelMapper modelMapper = new ModelMapper();
            Products newProduct=modelMapper.map(product, Products.class);
            if(file.isEmpty()){
                System.out.println("File is empty");
            }else{
                newProduct.setProduct_image(file.getOriginalFilename());
                File saveFile=new ClassPathResource("/static/image").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File is uploaded successfully");
            }
            newProduct.setCreated_by(user.getFirst_name()+" "+user.getLast_name());
            newProduct.setCreated_at(new java.sql.Timestamp(new Date().getTime()));
            newProduct.setCategory(categoryRepository.getCategoryById(cid));
            productRepository.save(newProduct);
        }
        catch (Exception e){

        }
    }
}
