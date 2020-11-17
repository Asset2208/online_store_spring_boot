package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Categories;
import kz.asset.online_store_asset_baiturinov.models.Countries;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageCategoriesController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam(name = "name") String name,
                           @RequestParam(name = "logo") String logo){
        Categories category = itemService.getCategoryByName(name);
        if (category == null) {
            itemService.addCategory(new Categories(null, name, logo));
        }

        return "redirect:/admin_categories";
    }

    @PostMapping("/editCategory")
    public String editCategory(@RequestParam(name = "category_id") Long category_id,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "logo") String logo){
        Categories category = itemService.getCategoryByName(name);

        Categories category_initial = itemService.getCategory(category_id);
        if (category_initial != null){
            category_initial.setLogoURL(logo);
            if (category == null) {
                category_initial.setName(name);
            }
            itemService.saveCategory(category_initial);
        }

        return "redirect:/admin_categories";
    }

    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam(name = "category_id") Long category_id){
        Categories category_initial = itemService.getCategory(category_id);
        if (category_initial != null) {
            itemService.deleteCategory(category_initial);
        }

        return "redirect:/admin_categories";
    }
}
