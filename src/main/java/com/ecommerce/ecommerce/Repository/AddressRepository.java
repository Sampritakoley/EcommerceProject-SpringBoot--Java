package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
