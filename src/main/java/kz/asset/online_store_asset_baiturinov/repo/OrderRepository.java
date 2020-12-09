package kz.asset.online_store_asset_baiturinov.repo;

import kz.asset.online_store_asset_baiturinov.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
