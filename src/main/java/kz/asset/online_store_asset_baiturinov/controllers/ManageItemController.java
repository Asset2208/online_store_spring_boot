package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Categories;
import kz.asset.online_store_asset_baiturinov.models.Countries;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/add")
    public String add(Model model)
    {
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
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
                          @RequestParam(name = "in_top") boolean in_top,
                          @RequestParam(name = "brand") Long brand_id){
        Brands brand = itemService.getBrand(brand_id);
        if (brand != null) {
            Date added_date = new Date(System.currentTimeMillis());
            ShopItem item = new ShopItem();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(rating);
            item.setSmallPictureUrl(small_picture_url);
            item.setLargePictureUrl(large_picture_url);
            item.setInTopPage(in_top);
            item.setBrand(brand);
            item.setAddedDate(added_date);
            itemService.addItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
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
                          @RequestParam(name = "in_top") boolean in_top,
                           @RequestParam(name = "brand") Long brand_id){

        Brands brand = itemService.getBrand(brand_id);
        ShopItem item = itemService.getItem(id);
        if (item != null && brand != null) {
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(rating);
            item.setSmallPictureUrl(small_picture_url);
            item.setLargePictureUrl(large_picture_url);
            item.setInTopPage(in_top);
            item.setBrand(brand);
            itemService.saveItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
    }

    @GetMapping("/item/{Id}")
    public String getItem(Model model,
                          @PathVariable(name = "Id") Long id){
        ShopItem item = itemService.getItem(id);
        model.addAttribute("item", item);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "details";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam(name = "id") Long id, Model model){
        ShopItem item = itemService.getItem(id);
        if (item != null) {
            itemService.deleteItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
    }

    @PostMapping("/assigncategory")
    public String assignCategory(Model model, @RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id){
        Categories cat = itemService.getCategory(category_id);
        if (cat != null){
            ShopItem item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);

                itemService.saveItem(item);
                return "redirect:/admin_items/" + item_id;
            }
        }
        return "redirect:/admin_items";
    }

    @PostMapping("/declinecategory")
    public String declinecategory(Model model, @RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id){
        Categories cat = itemService.getCategory(category_id);
        if (cat != null){
            ShopItem item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                categories.remove(cat);

                itemService.saveItem(item);
                return "redirect:/admin_items/" + item_id;
            }
        }
        return "redirect:/admin_items";
    }

}
