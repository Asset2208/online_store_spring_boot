package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Countries;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
