package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.Brands;
import kz.asset.online_store_asset_baiturinov.models.Categories;
import kz.asset.online_store_asset_baiturinov.models.Roles;
import kz.asset.online_store_asset_baiturinov.models.Users;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import kz.asset.online_store_asset_baiturinov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ItemService itemService;


    @GetMapping("/registration")
    public String registration(Model model) {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, @RequestParam("user_email") String email,
                          @RequestParam("user_password") String password,
                          @RequestParam("user_repassword") String re_password,
                          @RequestParam("full_name") String full_name){

        if (!password.equals(re_password)){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        Users user = new Users(null, email, password, full_name, null);
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserAdmin(Model model, RedirectAttributes redirectAttrs, @RequestParam("user_email") String email,
                          @RequestParam("user_password") String password,
                          @RequestParam("user_repassword") String re_password,
                          @RequestParam("full_name") String full_name){

        if (!password.equals(re_password)){
            redirectAttrs.addFlashAttribute("passwordError", "Пароли не совпадают");
            return "redirect:/admin_users?passworderror";
        }
        Users user = new Users(null, email, password, full_name, null);
        if (!userService.saveUser(user)){
            redirectAttrs.addFlashAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "redirect:/admin_users?username_error";
        }

        return "redirect:/admin_users?success_user_add";
    }

    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRole(RedirectAttributes redirectAttrs,
                          @RequestParam("role") String role){
        Roles exist_role = userService.getRoleByName(role);
        if (exist_role == null){
            Roles roles = new Roles(null, role);
            userService.addRole(roles);
            return "redirect:/admin_roles?success_role_add";
        }
        return "redirect:/admin_roles?role_error";
    }

    @PostMapping("/editRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editRole(RedirectAttributes redirectAttrs,
                          @RequestParam("role") String role,
                           @RequestParam("role_id") Long role_id){
        Roles current_role = userService.getRole(role_id);
        Roles new_role = userService.getRoleByName(role);
        if (new_role == null){
            current_role.setRole(role);
            userService.addRole(current_role);
            return "redirect:/admin_roles?success_role_add";
        }
        return "redirect:/admin_roles?role_error";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("currentUser", getUserData());
        return "login";
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

    @PostMapping("/editprofile")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Model model, @RequestParam("email") String email,
                              @RequestParam("full_name") String full_name, RedirectAttributes redirectAttrs){
        Users user = getUserData();
        if (user != null){
            user.setFullName(full_name);
            userService.editUser(user);
            redirectAttrs.addFlashAttribute("currentUser", getUserData());

            List<Brands> brands = itemService.getAllBrands();
            redirectAttrs.addFlashAttribute("brands", brands);

            List<Categories> categories = itemService.getAllCategories();
            redirectAttrs.addFlashAttribute("categories", categories);

            return "redirect:/profile?success_fullname";
        }
        return "redirect:/profile?error";
    }

    @PostMapping("/editpassword")
    @PreAuthorize("isAuthenticated()")
    public String editPassword(Model model, @RequestParam("old_password") String old_password,
                               @RequestParam("new_password") String new_password,
                               @RequestParam("re_new_password") String re_new_password,
                               RedirectAttributes redirectAttrs){
        Users user = getUserData();
        if (user != null){
            if (passwordEncoder.matches(old_password, user.getPassword())){
                if (new_password.equals(re_new_password)){
                    user.setPassword(passwordEncoder.encode(new_password));
                    userService.editUser(user);

                    List<Brands> brands = itemService.getAllBrands();
                    redirectAttrs.addFlashAttribute("brands", brands);

                    List<Categories> categories = itemService.getAllCategories();
                    redirectAttrs.addFlashAttribute("categories", categories);

                    redirectAttrs.addFlashAttribute("currentUser", getUserData());

                    return "redirect:/profile?success_password";
                }
                return "redirect:/profile?error_newpassword";
            }
            return "redirect:/profile?error_oldpassword";
        }
        return "redirect:/profile?error";
    }

}
