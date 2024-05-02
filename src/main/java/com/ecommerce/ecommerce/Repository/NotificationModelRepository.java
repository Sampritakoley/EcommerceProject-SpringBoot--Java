package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Dto.NotificationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationModelRepository extends MongoRepository<NotificationModel,Integer> {
    NotificationModel findByType(String type);
}
