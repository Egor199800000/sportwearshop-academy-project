package ru.sportswearcompany.sportswearsshop.controllers;


import ru.sportswearcompany.sportswearsshop.service.ItemService;
import ru.sportswearcompany.sportswearsshop.service.SearchService;
import ru.sportswearcompany.sportswearsshop.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final UserService userService;
    private final ItemService itemService;
    private final SearchService searchService;

    @GetMapping("")
    public String getHome(Model model){
        model.addAttribute("items",itemService.getAllActiveItems());
        return "home";
    }

    @RequestMapping("sort")
    public String indexWithQuery(@RequestParam("query") String query,
                                 @RequestParam("field") String field,
                                 Model model){
       if (query.equals("")){
           switch (field){
               case "name":
                   model.addAttribute("items",searchService.sortAllByName());
                   break;
               case "category":
                   model.addAttribute("items",searchService.sortAllByCategory());
                   break;
               case "brand":
                   model.addAttribute("items",searchService.sortAllByBrand());
                   break;
               case "recent":
                   model.addAttribute("items",searchService.sortAllByIdDesc());
                   break;
               case "priceAsc":
                   model.addAttribute("items",searchService.sortAllByPriceAsc());
               case "priceDesc":
                   model.addAttribute("items",searchService.sortAllByPriceDesc());
           }
       }
       else {
           switch (field){
               case "name":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByName(query));
                   break;
               case "category":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByCategory(query));
                   break;
               case "brand":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByBrand(query));
                   break;
               case "priceAsc":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByPriceAsc(query));
                   break;
               case "priceDesc":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByPriceDesc(query));
                   break;
               case "recent":
                   model.addAttribute("items",
                           searchService.getSearchItemsOrderByIdDesc(query));
                   break;
           }
       }
       model.addAttribute("field",field);
       model.addAttribute("query",query);

       return "home";
    }

    @GetMapping("search")
    public String navSearch(@RequestParam("query") String query,Model model){
        model.addAttribute("items",searchService.getSearchItems(query));
        model.addAttribute("query",query);
        return "home";
    }





}
