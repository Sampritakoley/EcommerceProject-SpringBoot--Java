package com.ecommerce.ecommerce.CustomRepository;

import com.ecommerce.ecommerce.Dto.ItemModel;
import com.ecommerce.ecommerce.Entities.Items;

import java.util.List;
import java.util.Map;

public interface ItemRepositoryCustom {
    List<Object[]> getItemByUser(int cartId, int userId,int productId);

    public List<Items> getSearchItems(String key, int limit, int offset);

    List<Object[]> getItemByItemId(int cartId,int userId,int ItemId);
}
