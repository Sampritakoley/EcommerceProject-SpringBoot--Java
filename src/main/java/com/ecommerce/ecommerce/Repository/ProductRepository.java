package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Products;
import com.ecommerce.ecommerce.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Integer> {


    @Query(value = "from Products p where p.category.id=:cid")
    public Page<Products> getProductByCategoryId(@Param("cid")int cid, Pageable pePageable);

    @Transactional
    @Query(value = "select DISTINCT p.* from products p where id=:pid",nativeQuery = true)
    Products getProductById(@Param("pid")int pid);
}
