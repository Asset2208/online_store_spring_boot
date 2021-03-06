package kz.asset.online_store_asset_baiturinov.service.impl;

import kz.asset.online_store_asset_baiturinov.models.*;
import kz.asset.online_store_asset_baiturinov.repo.*;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ShopItem addItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public List<ShopItem> getAllItems() {
        return shopItemRepository.findAll();
    }

    @Override
    public ArrayList<ShopItem> getAllItemsInTop() {
        return shopItemRepository.findAllByInTopPageIsTrue();
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByNameLikeOrderByPriceAsc(String name) {
        return shopItemRepository.findAllByNameContainingOrderByPriceAsc(name);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByBrandIdAndPriceBetweenAsc(Long id,double price1, double price2) {
        return shopItemRepository.findAllByBrandIdAndPriceBetweenOrderByPriceAsc(id, price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByBrandIdAndPriceBetweenDesc(Long id, double price1, double price2) {
        return shopItemRepository.findAllByBrandIdAndPriceBetweenOrderByPriceDesc(id, price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByNameLikeAndBrandIdAndPriceBetweenAsc(String name, Long id, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndBrandIdAndPriceBetweenOrderByPriceAsc(name, id, price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByNameLikeAndBrandIdAndPriceBetweenDesc(String name, Long id, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndBrandIdAndPriceBetweenOrderByPriceDesc(name, id, price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByBrandId(Long id) {
        return shopItemRepository.findAllByBrandId(id);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByCategoryId(Long id) {
        return shopItemRepository.findAllByCategoriesId(id);
    }

    @Override
    public ShopItem getItem(Long id) {
        return shopItemRepository.getOne(id);
    }

    @Override
    public void deleteItem(ShopItem item) {
        shopItemRepository.delete(item);
    }

    @Override
    public ShopItem saveItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public Countries addCountry(Countries country) {
        return countriesRepository.save(country);
    }

    @Override
    public List<Countries> getAllCountries() {
        return countriesRepository.findAll();
    }

    @Override
    public Countries getCountry(Long id) {
        return countriesRepository.getOne(id);
    }

    @Override
    public void deleteCountry(Countries country) {
        countriesRepository.delete(country);
    }

    @Override
    public Countries saveCountry(Countries country) {
        return countriesRepository.save(country);
    }

    @Override
    public Countries getCountryByName(String name) {
        return countriesRepository.findByName(name);
    }

    @Override
    public Brands addBrand(Brands brand) {
        return brandsRepository.save(brand);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsRepository.findAll();
    }

    @Override
    public Brands getBrand(Long id) {
        return brandsRepository.getOne(id);
    }

    @Override
    public void deleteBrand(Brands brand) {
        brandsRepository.delete(brand);
    }

    @Override
    public Brands saveBrand(Brands brand) {
        return brandsRepository.save(brand);
    }

    @Override
    public Brands getBrandByName(String name) {
        return brandsRepository.findByName(name);
    }

    @Override
    public Categories addCategory(Categories category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public void deleteCategory(Categories category) {
        categoryRepository.delete(category);
    }

    @Override
    public Categories saveCategory(Categories category) {
        return categoryRepository.save(category);
    }

    @Override
    public Categories getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Pictures addPicture(Pictures picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Pictures> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Pictures getPicture(Long id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public void deletePicture(Pictures picture) {
        pictureRepository.delete(picture);
    }

    @Override
    public Pictures savePicture(Pictures picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Pictures> getPicturesByItemId(Long id) {
        return pictureRepository.findByShopItemId(id);
    }

    @Override
    public Orders addOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Orders getOrder(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void deleteOrder(Orders order) {
        orderRepository.delete(order);
    }

    @Override
    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public Comments addComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comments getComment(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public void deleteComment(Comments comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comments saveComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comments> getCommentsByItemId(Long item_id) {
        return commentRepository.findAllByItemIdOrderByAddedDateDesc(item_id);
    }
}
