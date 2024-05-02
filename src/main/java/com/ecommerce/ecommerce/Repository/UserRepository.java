package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Transactional
    @Query(value = "select DISTINCT u.* from user u where email=:email",nativeQuery = true)
    public User getUserByEmail(@Param("email")String email);
}
