package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Countries;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageCountriesController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/addCountry")
    public String addCountryPage(){
        return "addCountry";
    }

    @PostMapping("/addCountry")
    public String addCountry(@RequestParam(name = "name") String name,
                             @RequestParam(name = "code") String code){
        Countries country = new Countries(null, name, code);
        itemService.addCountry(country);
        return "redirect:/";
    }
}
