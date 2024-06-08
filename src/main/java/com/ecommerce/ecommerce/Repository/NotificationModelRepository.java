package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Dto.NotificationModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationModelRepository extends MongoRepository<NotificationModel,Integer> {
    NotificationModel findByType(String type);
}
