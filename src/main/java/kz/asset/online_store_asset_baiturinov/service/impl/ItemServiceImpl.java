package kz.asset.online_store_asset_baiturinov.service.impl;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

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
    public ArrayList<ShopItem> getAllItemsPriceBetweenAsc(double price1, double price2) {
        return shopItemRepository.findAllByPriceBetweenOrderByPriceAsc(price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsPriceBetweenDesc(double price1, double price2) {
        return shopItemRepository.findAllByPriceBetweenOrderByPriceDesc(price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByNameLikeAndPriceBetweenAsc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndPriceBetweenOrderByPriceAsc(name, price1, price2);
    }

    @Override
    public ArrayList<ShopItem> getAllItemsByNameLikeAndPriceBetweenDesc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndPriceBetweenOrderByPriceDesc(name, price1, price2);
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


}
