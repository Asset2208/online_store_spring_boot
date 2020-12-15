package kz.asset.online_store_asset_baiturinov.service;

import kz.asset.online_store_asset_baiturinov.models.*;
import org.hibernate.criterion.Order;

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
    ArrayList<ShopItem> getAllItemsByCategoryId(Long id);

    Countries addCountry(Countries country);
    List<Countries> getAllCountries();
    Countries getCountry(Long id);
    void deleteCountry(Countries country);
    Countries saveCountry(Countries country);
    Countries getCountryByName(String name);

    Brands addBrand(Brands brand);
    List<Brands> getAllBrands();
    Brands getBrand(Long id);
    void deleteBrand(Brands brand);
    Brands saveBrand(Brands brand);
    Brands getBrandByName(String name);

    Categories addCategory(Categories category);
    List<Categories> getAllCategories();
    Categories getCategory(Long id);
    void deleteCategory(Categories category);
    Categories saveCategory(Categories category);
    Categories getCategoryByName(String name);
//    List<Categories> getAllCategoriesNotIn(List<Categories> categories);

    Pictures addPicture(Pictures picture);
    List<Pictures> getAllPictures();
    Pictures getPicture(Long id);
    void deletePicture(Pictures picture);
    Pictures savePicture(Pictures picture);
    List<Pictures> getPicturesByItemId(Long id);

    Orders addOrder(Orders order);
    List<Orders> getAllOrders();
    Orders getOrder(Long id);
    void deleteOrder(Orders order);
    Orders saveOrder(Orders order);

    Comments addComment(Comments comment);
    List<Comments> getAllComments();
    Comments getComment(Long id);
    void deleteComment(Comments comment);
    Comments saveComment(Comments comment);
    List<Comments> getCommentsByItemId(Long item_id);

}
