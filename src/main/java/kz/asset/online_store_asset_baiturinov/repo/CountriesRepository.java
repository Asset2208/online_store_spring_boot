package kz.asset.online_store_asset_baiturinov.repo;

import kz.asset.online_store_asset_baiturinov.models.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CountriesRepository extends JpaRepository<Countries, Long> {
    Countries findByName(String name);
}
