package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.IService.BrandServiceInterface;
import com.ecommerce.ecommerce.Repository.BrandRepository;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
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
public class BrandService implements BrandServiceInterface {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    public void addBrand(MultipartFile file, String descriptions, String brand_name, User user, Integer cid)
    {
        try
        {
            Category cat=categoryRepository.getCategoryById(cid);
            Brands newBrand=new Brands();
            newBrand.setBrand_name(brand_name);
            newBrand.setDescriptions(descriptions);
            if(file.isEmpty()){
                System.out.println("File is empty");
            }else{
                newBrand.setBrand_logo(file.getOriginalFilename());
                File saveFile=new ClassPathResource("/static/image").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File is uploaded successfully");
            }
            newBrand.setCreated_by(user.getFirst_name()+" "+user.getLast_name());
            newBrand.setCreated_at(new java.sql.Timestamp(new Date().getTime()));
            newBrand.setCategory(cat);
            brandRepository.save(newBrand);
        }catch(Exception e)
        {

        }

    }
}
