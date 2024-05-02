package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Brands;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brands,Integer> {
    @Transactional
    @Query(value = "select DISTINCT b.* from brands b where category_id=:cid",nativeQuery = true)
    List<Brands> getAllBrandByCategoryId(@Param("cid")int cid);

    @Transactional
    @Query(value = "select DISTINCT b.* from brands b where brand_name=:brand_name",nativeQuery = true)
    Brands getBrandByName(@Param("brand_name")String brand_name);
}
