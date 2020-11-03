package kz.asset.online_store_asset_baiturinov.service;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;

import java.util.ArrayList;
import java.util.List;

public interface ItemService {

    ShopItem addItem(ShopItem shopItem);
    List<ShopItem> getAllItems();
    ShopItem getItem(Long id);
    void deleteItem(ShopItem item);
    ShopItem saveItem(ShopItem item);
    ArrayList<ShopItem> getAllItemsInTop();

}
