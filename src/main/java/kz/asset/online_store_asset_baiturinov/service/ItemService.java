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
    ArrayList<ShopItem> getAllItemsByNameLikeOrderByPriceAsc(String name);
    ArrayList<ShopItem> getAllItemsPriceBetweenAsc(double price1, double price2);
    ArrayList<ShopItem> getAllItemsPriceBetweenDesc(double price1, double price2);
    ArrayList<ShopItem> getAllItemsByNameLikeAndPriceBetweenAsc(String name, double price1, double price2);
    ArrayList<ShopItem> getAllItemsByNameLikeAndPriceBetweenDesc(String name, double price1, double price2);
}
