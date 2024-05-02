package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Dto.RegisterDataDto;
import com.ecommerce.ecommerce.Entities.Cart;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User addUser(RegisterDataDto registerDataDto, MultipartFile file)
    {
        try{
            ModelMapper modelMapper = new ModelMapper();
            User user=modelMapper.map(registerDataDto, User.class);
            if(file.isEmpty()){
                System.out.println("File is empty");
            }else{
                user.setImage(file.getOriginalFilename());
                File saveFile=new ClassPathResource("/static/image").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                    Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File is uploaded successfully");
            }
            user.setPassword(passwordEncoder.encode(registerDataDto.getPassword()));
            Cart cart=new Cart();
            cart.setStatus("active");
            cart.setTotalAmount(0.0);
            cart.setUser(user);
            cart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
            user.getCarts().add(cart);
            user.setActive(true);
            userRepository.save(user);
            return user;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
