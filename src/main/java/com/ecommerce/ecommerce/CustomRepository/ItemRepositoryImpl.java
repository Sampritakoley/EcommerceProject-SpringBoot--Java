package com.ecommerce.ecommerce.CustomRepository;

import com.ecommerce.ecommerce.Dto.ItemModel;
import com.ecommerce.ecommerce.Entities.Items;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final EntityManager entityManager;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Object[]> getItemByUser(int cartId, int userId,int productId) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getItemByUser");
        storedProcedure.registerStoredProcedureParameter("cartId", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("productId", Integer.class, ParameterMode.IN);
        storedProcedure.setParameter("cartId", cartId);
        storedProcedure.setParameter("userId", userId);
        storedProcedure.setParameter("productId", productId);
        storedProcedure.execute();
        List<Object[]> resultList = storedProcedure.getResultList();
        return resultList;
    }

    public List<Items> getSearchItems(String key, int limit, int offset)
    {
        String query = "select i.* from ecommerce_db.items as i inner join ecommerce_db.products as p on i.products_id=p.id inner join ecommerce_db.category as c on p.category_id=c.id where LOWER(i.name) like '"+key+"%' || LOWER(p.product_name) like '"+key+"%' || LOWER(c.name) like '"+key+"%'";
        return entityManager.createNativeQuery(query, Items.class).setMaxResults(limit)
                .setFirstResult(offset).getResultList();
    }


}
