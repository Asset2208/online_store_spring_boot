package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Countries;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManageBrandsController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/addBrand")
    public String addBrandPage(Model model) {
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);

        return "addBrand";
    }

    @PostMapping("/addBrand")
    public String addBrand(@RequestParam(name = "name") String name,
                           @RequestParam(name = "country") Long country_id){
        Countries country = itemService.getCountry(country_id);
        Brands brand = itemService.getBrandByName(name);
        if (country != null && brand == null) {
            itemService.addBrand(new Brands(null, name, country));
        }

        return "redirect:/admin";
    }

    @PostMapping("/editBrand")
    public String editBrand(@RequestParam(name = "brand_id") Long brand_id,
                            @RequestParam(name = "name") String name,
                           @RequestParam(name = "country") Long country_id){
        Countries country = itemService.getCountry(country_id);
        Brands brand = itemService.getBrandByName(name);

        Brands brand_initial = itemService.getBrand(brand_id);
        if (brand_initial != null){
            if (country != null) {
                brand_initial.setCountry(country);
                if (brand == null){
                    brand_initial.setName(name);
                }
                itemService.saveBrand(brand_initial);
            }
        }

        return "redirect:/admin";
    }

    @PostMapping("/deleteBrand")
    public String deleteBrand(@RequestParam(name = "brand_id") Long brand_id){
        Brands brand_initial = itemService.getBrand(brand_id);
        if (brand_initial != null) {
            itemService.deleteBrand(brand_initial);
        }

        return "redirect:/admin";
    }

}
