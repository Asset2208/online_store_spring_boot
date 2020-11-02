package kz.asset.online_store_asset_baiturinov.repo;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ShopItemRepository extends CrudRepository<ShopItem, Long> {
    public ArrayList<ShopItem> findByName(String name);
}
