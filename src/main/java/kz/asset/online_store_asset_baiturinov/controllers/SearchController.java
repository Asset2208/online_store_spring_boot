package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

        model.addAttribute("brand_result", 0);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "search";
    }

    @GetMapping("/searchbrand/{Id}")
    public String brandSearch(Model model, @PathVariable(name = "Id") Long id){
        if (id != null){
            ArrayList<ShopItem> shopItems = itemService.getAllItemsByBrandId(id);
            model.addAttribute("items", shopItems);

            model.addAttribute("brand_result", id);

            List<Brands> brands = itemService.getAllBrands();
            model.addAttribute("brands", brands);

            return "search";
        }
        else {
            return "redirect:/";
        }

    }

    @GetMapping("/price")
    public String priceSearch(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                              @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                              @RequestParam(name = "price_to", defaultValue = "100000000") double price_to,
                              @RequestParam(name = "brand_search") Long brand_id){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndBrandIdAndPriceBetweenAsc(name, brand_id, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsByBrandIdAndPriceBetweenAsc(brand_id, price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);

        model.addAttribute("brand_result", brand_id);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "search";
    }

    @GetMapping("/sortasc")
    public String sortAsc(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                          @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                          @RequestParam(name = "price_to", defaultValue = "100000000") double price_to,
                          @RequestParam(name = "brand_search") Long brand_id){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndBrandIdAndPriceBetweenAsc(name, brand_id, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsByBrandIdAndPriceBetweenAsc(brand_id, price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);

        model.addAttribute("brand_result", brand_id);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "search";
    }

    @GetMapping("/sortdesc")
    public String sortDesc(Model model, @RequestParam(name = "search_result", defaultValue = "") String name,
                          @RequestParam(name = "price_from", defaultValue = "0") double price_from,
                          @RequestParam(name = "price_to", defaultValue = "100000000") double price_to,
                           @RequestParam(name = "brand_search") Long brand_id){
        ArrayList<ShopItem> shopItems;
        if (!name.equals("")){
            shopItems = itemService.getAllItemsByNameLikeAndBrandIdAndPriceBetweenDesc(name, brand_id, price_from, price_to);
        }
        else {
            shopItems = itemService.getAllItemsByBrandIdAndPriceBetweenDesc(brand_id, price_from, price_to);
        }
        model.addAttribute("search_item", name);
        model.addAttribute("price_from_searched", price_from);
        model.addAttribute("price_to_searched", price_to);
        model.addAttribute("items", shopItems);

        model.addAttribute("brand_result", 0);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "search";
    }
}
