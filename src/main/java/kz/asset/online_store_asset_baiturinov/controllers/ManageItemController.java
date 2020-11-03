package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class ManageItemController {

    @Autowired
    private ItemService itemService;

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
                          @RequestParam(name = "small_picture_url") String small_picture_url,
                          @RequestParam(name = "large_picture_url") String large_picture_url,
                          @RequestParam(name = "in_top") boolean in_top){

        Date added_date = new Date(System.currentTimeMillis());
        itemService.addItem(new ShopItem(null, name, description, price, amount, rating, small_picture_url, large_picture_url, added_date, in_top));
        return "redirect:/";
    }

    @PostMapping("/saveItem")
    public String saveItem(Model model,
                           @RequestParam(name = "id")Long id,
                          @RequestParam(name = "name")String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "rating") int rating,
                          @RequestParam(name = "small_picture_url") String small_picture_url,
                          @RequestParam(name = "large_picture_url") String large_picture_url,
                          @RequestParam(name = "in_top") boolean in_top){

        ShopItem item = itemService.getItem(id);
        if (item != null) {
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(rating);
            item.setSmallPictureUrl(small_picture_url);
            item.setLargePictureUrl(large_picture_url);
            item.setInTopPage(in_top);
            itemService.saveItem(item);
        }
        return "redirect:/";
    }

    @GetMapping("/item/{Id}")
    public String getItem(Model model,
                          @PathVariable(name = "Id") Long id){
        ShopItem item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "details";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam(name = "id") Long id){
        ShopItem item = itemService.getItem(id);
        if (item != null) {
            itemService.deleteItem(item);
        }
        return "redirect:/";
    }

}
