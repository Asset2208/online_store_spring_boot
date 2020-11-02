package kz.asset.online_store_asset_baiturinov.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int amount;
    private int stars;
    private String pictureUrl;

    public ShopItem(String name, String description, double price, int amount, int stars, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.stars = stars;
        this.pictureUrl = pictureUrl;
    }
}
