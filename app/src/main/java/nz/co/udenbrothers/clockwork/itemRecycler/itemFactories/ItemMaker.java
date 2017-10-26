package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;


public class ItemMaker {

    protected Context context;

    public ItemMaker(Context context){
        this.context = context;
    }

    public final List<Item> toItems(List<? extends Object> models, int type){
        List<Item> items = new ArrayList<>();
        for (Object model : models){
            Item item = new Item(type);
            item.model = model;
            items.add(item);
        }
        return items;
    }


    public final Item newItem(int type, Object model) {
        Item item = new Item(type);
        item.model = model;
        return item;
    }


    public final Item newItem(int type, String des) {
        Item item = new Item(type);
        item.des = des;
        return item;
    }
}
