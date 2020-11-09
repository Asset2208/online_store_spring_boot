package kz.asset.online_store_asset_baiturinov.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

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
    private String smallPictureUrl;
    private String largePictureUrl;
    private Date addedDate;
    private boolean inTopPage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brands brand;

}
