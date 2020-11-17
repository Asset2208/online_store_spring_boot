package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Categories;
import kz.asset.online_store_asset_baiturinov.models.Countries;
import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/admin")
    public String adminBrands(Model model) {
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "admin_brands";
    }

    @GetMapping("/admin_countries")
    public String adminCountries(Model model) {
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);

        return "admin_countries";
    }

    @GetMapping("/admin_items")
    public String adminItems(Model model) {
        List<ShopItem> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        return "admin_items";
    }

    @GetMapping("/admin_items/{Id}")
    public String item_details(Model model, @PathVariable(value = "Id") Long id){
        ShopItem item = itemService.getItem(id);
        if (item != null) {
            List<Brands> brands = itemService.getAllBrands();
            model.addAttribute("brands", brands);
            model.addAttribute("item", item);
            List<Categories> categories = itemService.getAllCategories();

            categories.removeIf(x -> item.getCategories().contains(x));

            model.addAttribute("categories", categories);

            return "details_item";
        }
        else {
            return "redirect:/admin_items";
        }
    }

    @GetMapping("/admin_categories")
    public String adminCategories(Model model) {
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        return "admin_categories";
    }
}
