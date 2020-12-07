package kz.asset.online_store_asset_baiturinov.controllers;

import kz.asset.online_store_asset_baiturinov.models.*;
import kz.asset.online_store_asset_baiturinov.repo.ShopItemRepository;
import kz.asset.online_store_asset_baiturinov.service.ItemService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageItemController {

    @Autowired
    private ItemService itemService;

    @Value("${file.item_photo.viewPath}")
    private String viewPath;

    @Value("${file.item_photo.uploadPath}")
    private String uploadPath;

    @Value("${file.item_photo.defaultPicture}")
    private String defaultPicture;

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String add(Model model)
    {
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "addItem";
    }

    @PostMapping("/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItem(Model model,
                          @RequestParam(name = "name")String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "rating") int rating,
                          @RequestParam(name = "small_picture_url") String small_picture_url,
                          @RequestParam(name = "large_picture_url") String large_picture_url,
                          @RequestParam(name = "in_top") boolean in_top,
                          @RequestParam(name = "brand") Long brand_id,
                          @RequestParam(name = "categories") List<Long> categoriesId){
        Brands brand = itemService.getBrand(brand_id);
        if (brand != null) {
            Date added_date = new Date(System.currentTimeMillis());
            ShopItem item = new ShopItem();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(rating);
            item.setSmallPictureUrl(small_picture_url);
            item.setLargePictureUrl(large_picture_url);
            item.setInTopPage(in_top);
            item.setBrand(brand);
            item.setAddedDate(added_date);
            ArrayList<Categories> categories = new ArrayList<>();
            for (Long category : categoriesId){
                categories.add(itemService.getCategory(category));
            }
            item.setCategories(categories);
            itemService.addItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
    }

    @PostMapping("/saveItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveItem(Model model,
                           @RequestParam(name = "id")Long id,
                          @RequestParam(name = "name")String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "rating") int rating,
                          @RequestParam(name = "small_picture_url") String small_picture_url,
                          @RequestParam(name = "large_picture_url") String large_picture_url,
                          @RequestParam(name = "in_top") boolean in_top,
                           @RequestParam(name = "brand") Long brand_id){

        Brands brand = itemService.getBrand(brand_id);
        ShopItem item = itemService.getItem(id);
        if (item != null && brand != null) {
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(rating);
            item.setSmallPictureUrl(small_picture_url);
            item.setLargePictureUrl(large_picture_url);
            item.setInTopPage(in_top);
            item.setBrand(brand);
            itemService.saveItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
    }

    @GetMapping("/item/{Id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String getItem(Model model,
                          @PathVariable(name = "Id") Long id){
        ShopItem item = itemService.getItem(id);
        model.addAttribute("item", item);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        return "details";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteItem(@RequestParam(name = "id") Long id, Model model){
        ShopItem item = itemService.getItem(id);
        if (item != null) {
            itemService.deleteItem(item);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "redirect:/admin_items";
    }

    @PostMapping("/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignCategory(Model model, @RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id){
        Categories cat = itemService.getCategory(category_id);
        if (cat != null){
            ShopItem item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);

                itemService.saveItem(item);
                return "redirect:/admin_items/" + item_id;
            }
        }
        return "redirect:/admin_items";
    }

    @PostMapping("/declinecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String declinecategory(Model model, @RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id){
        Categories cat = itemService.getCategory(category_id);
        if (cat != null){
            ShopItem item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                categories.remove(cat);

                itemService.saveItem(item);
                return "redirect:/admin_items/" + item_id;
            }
        }
        return "redirect:/admin_items";
    }

    @PostMapping("/upload_item_photo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String uploadItemPhoto(@RequestParam("item_photo") MultipartFile file,
                                  @RequestParam("item_id") Long item_id){
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
            try {

                ShopItem item = itemService.getItem(item_id);

                List<Pictures> pictures = itemService.getPicturesByItemId(item_id);
                int size = pictures.size() + 1;
                String picName = DigestUtils.sha1Hex("itemphoto_" + item.getId() + "_" + size + "_!Picture");

                byte []bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName + ".jpg");
                Files.write(path, bytes);

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                java.util.Date date = new java.util.Date();

                Pictures picture = new Pictures(null, picName, date, item);
                itemService.savePicture(picture);

                return "redirect:/admin_items/" + item_id;

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return "redirect:/";

    }

    @GetMapping(value = "/itemphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public @ResponseBody byte[] viewItemPhotos(@PathVariable("url") String url) throws IOException {

        String pictureURL = viewPath + defaultPicture;

        if (url != null && !url.equals("null")){
            pictureURL = viewPath + url+".jpg";
        }

        InputStream in;

        try {

            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();

        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath + defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);

    }

    @PostMapping("/deletepicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deletePicture(@RequestParam("picture_id") Long id,
                                @RequestParam("item_id") Long item_id){
        try {

            Pictures picture = itemService.getPicture(id);

            File file = new File(uploadPath + picture.getUrl() + ".jpg");
            new FileInputStream(file);
            System.gc();

            Files.delete(Paths.get(file.getPath()));

            itemService.deletePicture(picture);



            return "redirect:/admin_items";

        }catch (IOException e){
            e.printStackTrace();
        }



        return "redirect:/";

    }

}
