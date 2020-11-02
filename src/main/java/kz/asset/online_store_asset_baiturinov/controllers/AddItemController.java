package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddItemController {
    @Autowired
    private ShopItemRepository shopItemRepository;

    @GetMapping("/add")
    public String add(){
        return "addItem";
    }

    @PostMapping("/addItem")
    public String addItem(Model model,
                          @RequestParam(name = "name")String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "rating") int rating,
                          @RequestParam(name = "picture_url") String picture_url){

        ShopItem shopItem = new ShopItem(name, description, price, amount, rating, picture_url);
        shopItemRepository.save(shopItem);
        return "redirect:/";
    }


}
