package ru.sportswearcompany.sportswearsshop.controllers;


import ru.sportswearcompany.sportswearsshop.model.Item;
import ru.sportswearcompany.sportswearsshop.model.ItemImage;
import ru.sportswearcompany.sportswearsshop.model.User;
import ru.sportswearcompany.sportswearsshop.service.*;




@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final SearchService searchService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;


    @GetMapping("adminhome")
    public String adminHome(Model model){
        model.addAttribute("items", itemService.getAllItems());
        return "/admin/adminhome";
    }


//ЗАГРУЗКА КАРТИНКИ ТОВАРА
    @Transactional
    @PostMapping("upload")                    //?file=...&id=111
    public String singleFileUpload(@RequestParam("file") MultipartFile file
            ,@RequestParam("id") Long id
            ,RedirectAttributes redirectAttributes){

        Item currentItem=itemService.getItemById(id);

        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message",
                    "Data is empty! Please upload your image");
            return "redirect:../admin/adminView"+id;
        }
        String UPLOADED_FOLDER= "static/images/Images";

        Path destPath= Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
        try {
            file.transferTo(destPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String originalPathStr="/images/itemImages"+destPath.getFileName().toString();

        byte[] encodedPath= Base64.getEncoder().encode(originalPathStr.getBytes());
        ItemImage image=new ItemImage(currentItem,encodedPath);
        redirectAttributes.addAttribute("message",
                "You successfully loaded image "
                        +file.getOriginalFilename()
                        +" for "+currentItem.getName());

        itemImageService.saveItemImage(image);
        currentItem.getImages().add(image);
        itemService.saveItem(currentItem);
        return "redirect:../admin/adminView"+id;
    }

@GetMapping("adminView/{id}")
    public String adminView(Long id, Model model){
        model.addAttribute("item", itemService.getCartItemById(id));
        return "admin/adminView";
}


//УДАЛЕНИЕ КАРТИНКИ ТОВАРА
    @Transactional
    @RequestMapping("deleteImage")
    public String deleteImage(@RequestParam("imagePath") String imagePath
                              ,@RequestParam("itemId") Long itemId){
        Item item=itemService.getItemById(itemId);
        for (ItemImage itemImage: item.getImages()){
            if (itemImage.getDecodedImgPath().equals(imagePath)){
                itemImageService.deleteImageById(itemImage.getId());
                item.getImages().remove(itemImage);
                break;
            }
        }
        itemService.saveItem(item);
        return "redirect:../admin/adminView"+itemId;
    }


//СОРТИРОВКА ПО РАЗНЫМ ПАРАМЕТРАМ И ПО ВОЗР/УБЫВАНИЮ
@RequestMapping("sort")
    public String getAdminSortedItems(@RequestParam("query") String query
        ,@RequestParam("field") String field
        ,Model model){
        if (query.equals(null)) {
            switch (field) {
                case "name":
                    model.addAttribute("items", searchService.sortAllByName());
                case "category":
                    model.addAttribute("items", searchService.sortAllByCategory());
                case "brand":
                    model.addAttribute("items", searchService.sortAllByBrand());
                case "recent":
                    model.addAttribute("items", searchService.sortAllById());
                case "priceAsc":
                    model.addAttribute("items", searchService.sortAllByPriceAsc());
                case "priceDesc":
                    model.addAttribute("items", searchService.sortAllByPriceDesc());
            }
        }else {         //.../sort?query=null&field=
            switch (field){
                case "name":
                    model.addAttribute("items",searchService.getSearchItemsOrderByName(query));
                case "category":
                    model.addAttribute("items",searchService.getSearchItemsOrderByCategory(query));
                case "brand":
                    model.addAttribute("items",searchService.getSearchItemsOrderByBrand(query));
                case "recent":
                    model.addAttribute("items",searchService.sortAllByIdDesc());
                case "priceAsc":
                    model.addAttribute("items",searchService.getSearchItemsOrderByPriceAsc(query));
                case "priceDesc":
                    model.addAttribute("items",searchService.getSearchItemsOrderByPriceDesc(query));
            }
        }
        model.addAttribute("field",field);
        model.addAttribute("query",query);

        return "admin/adminhome";
    }

    @GetMapping("searchItems")
    public String navigationSearch(@RequestParam("query")String query, Model model){
        model.addAttribute("items",searchService.getSearchItems(query));
        model.addAttribute("query",query);
        return "admin/adminhome";
    }


    @GetMapping("searchUsers")
    public String searchUsers(@RequestParam("userSearch") String userSearch
            ,Model model){
        model.addAttribute("users",userService.getUserWithAdvanceSearch(userSearch));
        model.addAttribute("userSearch",userSearch);
        return "admin/adminhome";
    }


    @GetMapping("users")
    public String getAllUsers(Model model){
        model.addAttribute("users",userService.getAllUser());
        return "admin/adminhome";
    }

    @GetMapping("adminUser/{id}")
    public String seeUserDetails(@PathVariable("id") Long id, Model model){
        User user=userService.getUser(id);
        model.addAttribute("user",user);
        model.addAttribute("orders",
                shoppingCartService.getCompletedShoppingCarts(user.getUsername(),true));
        return "admin/adminView";
    }


    @GetMapping("enable/{id}")
    public String enable(@PathVariable("id")Long id,Model model){
        User user=userService.getUser(id);
        if (user.isEnable()){
            user.setEnable(false);
            model.addAttribute("activation",
                    user.getUsername()+" 's account blocked");
        }
        else {
            user.setEnable(true);
            model.addAttribute("activation",
                    user.getUsername()+" 's account unblocked");
        }
        model.addAttribute("users",userService.getAllUser());
        userService.saveUser(user);
        return "admin/adminhome";
    }

    @GetMapping("newItem")
    public String newItem(Model model){
        model.addAttribute("item",new Item());
        return "admin/newItem";
    }

    @PostMapping(value = "newItem")
    public String saveNewItem(@ModelAttribute Item item,
                              RedirectAttributes redirectAttributes){
        itemService.saveItem(item);
        redirectAttributes.addFlashAttribute("activation"
                ,item.getName()+" added successfully");
        return "redirect:../adminhome";
    }



//TODO:ПЕРЕВОД ТОВАРА В АКТИВНОЕ СОСТОЯНИЕ-ДОСТУПНОЕ ДЛЯ ЗАКАЗА
    @GetMapping("activation/{id}")
    public String activation(@PathVariable("id") Long id,
                             RedirectAttributes redirectAttributes){
        Item currentItem=itemService.getItemById(id);
        if (currentItem.isActive()){
            currentItem.setActive(false);
            redirectAttributes.addAttribute("activation",
                    currentItem.getName()+" is switched inactive status");
        }
        else {
            currentItem.setActive(true);
            redirectAttributes.addAttribute("activation",
                    currentItem.getName()+" is switched active status");

        }
        itemService.saveItem(currentItem);
        return "redirect:.../adminhome";
    }





}
