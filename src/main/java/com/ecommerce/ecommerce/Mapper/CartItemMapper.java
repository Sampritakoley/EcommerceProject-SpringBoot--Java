package com.ecommerce.ecommerce.Mapper;

import com.ecommerce.ecommerce.Dto.CartItemModel;
import com.ecommerce.ecommerce.Dto.ItemModel;
import com.ecommerce.ecommerce.Entities.Items;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemMapper {


   /* private List<CartItemModel> mapToCartItemModel(List<Items> itemList) {
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        for (Items item : itemList) {
            CartItemModel cartItemModel =CartItemModel.builder()
                    .cartItemId(1 )
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .image(item.getImage())
                    .available(item.isAvailable())
                    .price(item.getPrice())
                    .offer(item.getOffer())
                    .description(item.getDescription())
                    .brand(item.getBrand())
                    .build();

            cartItemModelList.add(cartItemModel);
        }
        return cartItemModelList;
    }*/

    public List<ItemModel> mapToitemModel(List<Object[]> items)
    {
        List<ItemModel> itemList=new ArrayList<>();
        for(Object[] item : items){
            ItemModel itemModel=new ItemModel();
            itemModel.setId(((Integer)item[0]).intValue());
            itemModel.setAvailable((boolean)item[1]);
            itemModel.setDescription((String)item[2]);
            itemModel.setName((String)item[3]);
            itemModel.setOffer((double)item[4]);
            itemModel.setPrice((double)item[5]);
            itemModel.setQuantity((Integer) item[6]);
            itemModel.setImage((String)item[7]);
            itemModel.setCid(((Long)item[8]).intValue());
            itemModel.setWishlist_id(((Long)item[9]).intValue());
            itemList.add(itemModel);
        }
        return itemList;
    }
}
