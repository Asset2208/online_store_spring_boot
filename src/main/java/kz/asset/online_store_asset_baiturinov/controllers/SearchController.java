package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/search")
    public String simpleSearch(Model model, @RequestParam(name = "search_result") String result){
        ArrayList<ShopItem> shopItems = itemService.getAllItemsByNameLikeOrderByPriceAsc(result);
        model.addAttribute("items", shopItems);
        model.addAttribute("search_item", result);
        model.addAttribute("price_from_searched", 0);
        model.addAttribute("price_to_searched", 100000000);
        return "search";
    }

    @GetMapping("/price")
    public String priceSearch(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                              @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                              @RequestParam(name = "price_to", defaultValue = "100000000") double price_to){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndPriceBetweenAsc(name, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsPriceBetweenAsc(price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);
        return "search";
    }

    @GetMapping("/sortasc")
    public String sortAsc(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                          @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                          @RequestParam(name = "price_to", defaultValue = "100000000") double price_to){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndPriceBetweenAsc(name, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsPriceBetweenAsc(price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);
        return "search";
    }

    @GetMapping("/sortdesc")
    public String sortDesc(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                          @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                          @RequestParam(name = "price_to", defaultValue = "100000000") double price_to){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndPriceBetweenDesc(name, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsPriceBetweenDesc(price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);
        return "search";
    }
}
