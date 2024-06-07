package com.ecommerce.ecommerce.Mapper;

import com.ecommerce.ecommerce.Dto.CartItemModel;
import com.ecommerce.ecommerce.Dto.ItemModel;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.Warehouse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CartItemMapper {

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
            itemModel.setBrand_id((Integer)item[8]);
            itemModel.setCid(((Long)item[9]).intValue());
            itemModel.setWishlist_id(((Long)item[10]).intValue());
            itemList.add(itemModel);
        }
        return itemList;
    }

    public List<ItemModel> mapItemToItemModel(List<Items> items){
        List<ItemModel> itemList=new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(Items item:items){
            ItemModel itemModel=modelMapper.map(item, ItemModel.class);
        }
        return itemList;
    }
}
