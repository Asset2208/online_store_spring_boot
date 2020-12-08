package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.*;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import kz.asset.online_store_asset_baiturinov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<ShopItem> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        return "index";
    }

    @GetMapping("/top")
    public String top(Model model) {
        ArrayList<ShopItem> items = itemService.getAllItemsInTop();
        model.addAttribute("items", items);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        return "index";
    }

    @GetMapping("/403")
    public String accessDenied(Model model){
        model.addAttribute("currentUser", getUserData());
        return "403";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser", getUserData());

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        return "profile";
    }
    @PostMapping("/add_to_basket")
    public String addToBasket(@RequestParam("item_id") Long id, HttpSession session){
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        if (basket == null) {
            basket = new ArrayList<>();
            session.setAttribute("basket", basket);
        }
        ShopItem item = itemService.getItem(id);
        if (basket.size() > 0){
            for (Basket basket1 : basket){
                if (basket1.getItem().getId().equals(id)){
                    basket1.setAmount(basket1.getAmount() + 1);
                    session.setAttribute("basket", basket);
                    return "redirect:/item/" + id;
                }
            }
        }
        basket.add(new Basket(item, 1));
        session.setAttribute("basket", basket);

        return "redirect:/item/" + id;
    }

    @GetMapping("/basket")
    public String basket(Model model, HttpSession session) {
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        double total = 0;
        if (basket == null){
            basket = new ArrayList<Basket>();
        }
        else {
            for (Basket b : basket){
                total += (b.getItem().getPrice() * b.getAmount());
            }
        }
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        model.addAttribute("currentUser", getUserData());

        model.addAttribute("total", total);

        model.addAttribute("baskets", basket);

        return "basket";
    }

    @PostMapping(value = "/addQuantity")
    public String addQuantity(HttpSession session,
                              @RequestParam(name = "id") Long id){
        List<Basket> items = (List<Basket>) session.getAttribute("basket");

        ShopItem item = itemService.getItem(id);
        Basket basket = new Basket();

        for(Basket b: items){
            if(b.getItem().getId().equals(id)){
                b.setAmount(b.getAmount() + 1);
                session.setAttribute("baskets", items);
                return "redirect:/basket";
            }
        }


        return "redirect:/basket";
    }

    @PostMapping(value = "/removeQuantity")
    public String removeQuantity(HttpSession session,
                              @RequestParam(name = "id") Long id){
        List<Basket> items = (List<Basket>) session.getAttribute("basket");

        ShopItem item = itemService.getItem(id);
        Basket basket = new Basket();

        for(Basket b: items){
            if(b.getItem().getId().equals(id)){
                if (b.getAmount() > 1){
                    b.setAmount(b.getAmount() - 1);
                }
                else {
                    items.remove(b);
                }
                session.setAttribute("baskets", items);
                return "redirect:/basket";
            }
        }


        return "redirect:/basket";
    }

    @PostMapping(value = "/clearBasket")
    public String clearBasket(HttpSession session){
        session.removeAttribute("basket");

        return "redirect:/basket";
    }

    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

}
