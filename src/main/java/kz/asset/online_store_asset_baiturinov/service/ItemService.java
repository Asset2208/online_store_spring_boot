package kz.asset.online_store_asset_baiturinov.service;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Countries;
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
    ArrayList<ShopItem> getAllItemsByBrandIdAndPriceBetweenAsc(Long id, double price1, double price2);
    ArrayList<ShopItem> getAllItemsByBrandIdAndPriceBetweenDesc(Long id, double price1, double price2);
    ArrayList<ShopItem> getAllItemsByNameLikeAndBrandIdAndPriceBetweenAsc(String name, Long id, double price1, double price2);
    ArrayList<ShopItem> getAllItemsByNameLikeAndBrandIdAndPriceBetweenDesc(String name, Long id, double price1, double price2);
    ArrayList<ShopItem> getAllItemsByBrandId(Long id);

    Countries addCountry(Countries country);
    List<Countries> getAllCountries();
    Countries getCountry(Long id);
    void deleteCountry(Countries country);
    Countries saveCountry(Countries country);

    Brands addBrand(Brands brand);
    List<Brands> getAllBrands();
    Brands getBrand(Long id);
    void deleteBrand(Brands brand);
    Brands saveBrand(Brands brand);

}
