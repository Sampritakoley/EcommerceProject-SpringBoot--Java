package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.CustomRepository.ItemRepositoryCustom;
import com.ecommerce.ecommerce.Dto.*;
import com.ecommerce.ecommerce.Entities.*;
import com.ecommerce.ecommerce.Enums.NotificationTypes;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.IService.ProductServiceInterface;
import com.ecommerce.ecommerce.Mapper.CartItemMapper;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Service.CategoryService;
import com.ecommerce.ecommerce.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ItemRepository itemRepository;
    @Autowired

    private ShippmentRepository shippmentRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepositoryCustom itemRepositoryCustom;
    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private NotificationModelRepository notificationModelRepository;

    @Autowired
    private ProductServiceInterface productServiceInterface;

    public void addModelAttribute(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Fetch user data based on username
            User user = userRepository.getUserByEmail(username);

            model.addAttribute("role", user.getRole());

            model.addAttribute("user", user);
            model.addAttribute("title", "Dashboard - ShopMart");
            if(Objects.equals(user.getRole(), "ROLE_USER")){
                model.addAttribute("baseUrl","member/base");
            }else if(Objects.equals(user.getRole(), "ROLE_ADMIN")){
                model.addAttribute("baseUrl","admin/base");
            }
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }

    @GetMapping("/products_list/{cid}/{page}")
    public String contactDetails(@PathVariable Integer cid,@PathVariable Integer page,Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        page = Math.max(0, page);
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Category cat=categoryRepository.getCategoryById(cid);
        model.addAttribute("CategoryName",cat.getName());
        Page<Products> productList=productServiceInterface.getProductByCategoryId(cid,pageable);
        model.addAttribute("totalPages", productList.getTotalPages());
        model.addAttribute("currentPage", page);
        List<Brands> brandList=brandRepository.getAllBrandByCategoryId(cid);
        if(!productList.isEmpty()){
            model.addAttribute("Products",productList.getContent());
        }
        if(!brandList.isEmpty()){
            model.addAttribute("Brands",brandList);
        }
        model.addAttribute("cid", cid);
        return "admin/admin_products";
    }

    @GetMapping("/product_form/{cid}")
    public String contactForm(@PathVariable Integer cid,Model model,Principal principal){
        addModelAttribute(model);
        model.addAttribute("Product",new ProductRequest());
        model.addAttribute("cid",cid);
        return "admin/admin_add_product";
    }


    @PostMapping("/product_form/{cid}")
    public String addProductForm(@PathVariable Integer cid,@Valid @ModelAttribute("Product") ProductRequest product,
                                 @RequestParam("profileImage") MultipartFile file,
                                 BindingResult bindingResult, HttpSession session,
                                 Model model, Principal principal)
    {
        try{
            if(bindingResult.hasErrors())
            {
                throw new Exception();
            }
            if(!bindingResult.hasErrors())
            {
                addModelAttribute(model);
                String userEmail=principal.getName();
                User user=userRepository.getUserByEmail(userEmail);
                productServiceInterface.addProduct(file,product,user,cid);
                session.setAttribute("message", new Message("New category is added successfully","alert-success"));
            }
        }
        catch (Exception e){
            addModelAttribute(model);
            session.setAttribute("message", new Message("Something went wrong","alert-danger"));
        }
        return "redirect:/products_list/"+cid+"/0";
    }


    @GetMapping("/category/{cid}/products/{pid}")
    public String getProductItems(@PathVariable Integer cid,@PathVariable Integer pid,@RequestParam(required = false, name = "brandName") String brandName,
                                  Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        Cart cart=cartRepository.getActiveCartByUserId(user.getId());
        List<Object[]> itemList=itemRepositoryCustom.getItemByUser(cart.getId(),user.getId(),pid);
        List<ItemModel> items=cartItemMapper.mapToitemModel(itemList);
        model.addAttribute("pid",pid);
        model.addAttribute("cid",cid);
        model.addAttribute("cartId",cart.getId());
        model.addAttribute("ItemList",items);
        return "admin/admin_item";
    }

    @GetMapping("/search/{key}/{page}")
    public String getItems(@PathVariable String key,@PathVariable int page,Model model)
    {
        addModelAttribute(model);
        int limit=8;
        int offset=0;
        if(page>=1)
        {
            offset=(page)*limit;
        }
        List<Items> itemList=itemRepositoryCustom.getSearchItems(key,limit,offset);
        int totalpage=(itemList.size()%limit==0)?itemList.size()/limit:(itemList.size()/limit)+1;
        model.addAttribute("ItemList",itemList);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalpage);
        return "admin/search_item";
    }

    @GetMapping("/product-item/{itemId}")
    public String getProductItems(@PathVariable Integer itemId,Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        Cart cart=cartRepository.getActiveCartByUserId(user.getId());
        List<Object[]> itemList=itemRepositoryCustom.getItemByItemId(cart.getId(),user.getId(),itemId);
        List<ItemModel> items=cartItemMapper.mapToitemModel(itemList);
        Optional<Brands> brand=brandRepository.findById(items.get(0).getBrand_id());
        model.addAttribute("item",items.get(0));
        model.addAttribute("brand",brand.get());
        return "admin/item_page";
    }
}
