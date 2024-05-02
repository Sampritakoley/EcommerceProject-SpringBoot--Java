package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


    @Query(value = "from Category c")
    Page<Category> getAllCategories(Pageable pePageable);



    @Transactional
    @Query(value="select DISTINCT c.* from category c where id=:cid",nativeQuery = true)
    Category getCategoryById(@Param("cid")int cid);
}
