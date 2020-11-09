package kz.asset.online_store_asset_baiturinov.repo;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

@Repository
@Transactional
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    ArrayList<ShopItem> findByName(String name);
    ArrayList<ShopItem> findAllByInTopPageIsTrue();
    ArrayList<ShopItem> findAllByNameContainingOrderByPriceAsc(String name);
    ArrayList<ShopItem> findAllByBrandIdAndPriceBetweenOrderByPriceAsc(Long id, double price1, double price2);
    ArrayList<ShopItem> findAllByBrandIdAndPriceBetweenOrderByPriceDesc(Long id, double price1, double price2);
    ArrayList<ShopItem> findAllByNameContainingAndBrandIdAndPriceBetweenOrderByPriceAsc(String name, Long id, double price1, double price2);
    ArrayList<ShopItem> findAllByNameContainingAndBrandIdAndPriceBetweenOrderByPriceDesc(String name, Long id, double price1, double price2);
    ArrayList<ShopItem> findAllByBrandId(Long id);
}
