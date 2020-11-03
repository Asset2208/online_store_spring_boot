package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.ShopItem;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String home(Model model, @CookieValue(value = "language", defaultValue = "ru") String language) {
        Iterable<ShopItem> items = itemService.getAllItems();
        model.addAttribute("items", items);
        model.addAttribute("language", language);
        return "index";
    }


}
