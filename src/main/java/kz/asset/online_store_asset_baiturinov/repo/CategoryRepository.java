package kz.asset.online_store_asset_baiturinov.repo;

import kz.asset.online_store_asset_baiturinov.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    Categories findByName(String name);
}
