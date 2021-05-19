package com.tvmshoppingcart.addtocart.contoller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tvmshoppingcart.addtocart.model.Addtocart;
import com.tvmshoppingcart.addtocart.model.Payment;
import com.tvmshoppingcart.addtocart.model.Product;
import com.tvmshoppingcart.addtocart.model.User;
import com.tvmshoppingcart.addtocart.service.AddtocartPaymentService;
import com.tvmshoppingcart.addtocart.service.AddtocartProductService;
import com.tvmshoppingcart.addtocart.service.AddtocartService;
import com.tvmshoppingcart.addtocart.service.AddtocartUserService;

import ch.qos.logback.classic.Logger;

@Controller
public class AddtocartController {
	@Autowired

	private AddtocartService addtocartService;
	@Autowired
	private AddtocartUserService addtocartUserService; 
	@Autowired
	private AddtocartProductService addtocartProductService;
	@Autowired
	private AddtocartPaymentService addtocartPaymentService;
	@GetMapping("/")
    public String viewAddtocart(Model model) {
        model.addAttribute("listAddtocart", addtocartService.getAllAddtocart());
        return "index";
	}
	@GetMapping("/addtocartfromproduct/{userid}/{productid}/{productname}/{productqty}/{productprice}")
    public String AddtocartfromProduct(@PathVariable(value = "userid") long userid,@PathVariable(value = "productid") long productid,@PathVariable(value = "productname") String productname,@PathVariable(value = "productqty") long productqty,@PathVariable(value = "productprice") long productprice) {
    	
		Addtocart addtocart=new Addtocart();
		String userid1=""+userid;
    	addtocart.setUserid(userid1);
    	addtocart.setUserid1(userid);
    	addtocart.setProductid(productid);
    	addtocart.setProductname(productname);
    	addtocart.setProductqty(productqty);
    	addtocart.setProductprice(productprice);
    	addtocart.setFinalprice(productqty*productprice);
    	addtocartService.saveAddtocart(addtocart);
    	return "redirect:/addtocartuser/{userid}";
    }
	
	@GetMapping("/deleteAddtocartItem/{userid}")
	public String DeleteAddtocartItem(@PathVariable(value="userid") long userid) {
		
		addtocartService.deleteAddtocartById(userid);
		return "redirect:/";
	}
	@PostMapping("/savePayment/{id}")
    public String savePayment(@ModelAttribute("payment") Payment payment,@PathVariable(value="id") long id) {
    	Addtocart addtocart=addtocartService.getAddtocartById(id);
    	long productid=addtocart.getProductid();
    	long productqty=addtocart.getProductqty();
		Product product=addtocartProductService.getProductById(productid);
    	long productqty1=product.getProductqtyavilabel();
    	if (productqty<=productqty1) {
    		productqty1 -=productqty;
    		product.setProductqtyavilabel(productqty1);
    		addtocartProductService.saveProduct(product);
    		addtocartPaymentService.saveAddtocartPayment(payment);
    		return "redirect:/productplaced/{id}";
    	}
    	else{
    		return "redirect:/outofproduct/{id}";
    	}
        
    }
	
    @GetMapping("/placepage/{id}")
    public String placePage(@PathVariable(value="id")long id,Model model) {
    	Addtocart addtocart=addtocartService.getAddtocartById(id);
        long userid2=addtocart.getUserid1();
        long productid=addtocart.getProductid();
    	User user=addtocartUserService.getUserById(userid2);
    	Payment payment=new Payment();
    	model.addAttribute("addtocart",addtocart);
    	model.addAttribute("user", user);
    	model.addAttribute("payment", payment);
    	model.addAttribute("productid", productid);
    	return "placepage";
    }
    
    @GetMapping("/addtocartuser/{userid}")
    public String AddtocartUser(@PathVariable(value="userid") long userid,Model model) {
    	String userid2=""+userid;
    	model.addAttribute("listAddtocart", addtocartService.getAllAddtocartByUserid(userid2));
    	model.addAttribute("userid2",userid);
    	
    	return "new";
    }
    @GetMapping("/updateAddtocartItem/{id}")
    public String updateAddtocartItem(@PathVariable(value="id") long id,Model model) {
    	Addtocart addtocart=addtocartService.getAddtocartById(id);
    	model.addAttribute("addtocart", addtocart);
    	return "updateqty";
    }
    @PostMapping("/saveAddtocart/{userid1}")
    public String saveAddtocart(@ModelAttribute("addtocart") Addtocart addtocart,@PathVariable(value="userid1") long userid1) {
    	long qty=addtocart.getProductqty();
    	long price=addtocart.getProductqty();
    	long finalprice=qty*price;
    	addtocart.setFinalprice(finalprice);
    	addtocartService.saveAddtocart(addtocart);
    	return "redirect:/addtocartuser/{userid1}";
    }
    @GetMapping("/outofproduct/{id}")
    public String outofProduct(@PathVariable(value="id") long id,Model model) {
    	Addtocart addtocart=addtocartService.getAddtocartById(id);
    	model.addAttribute("addtocart", addtocart);
    	return "outofproduct";
    }
    @GetMapping("/productplaced/{id}")
    public String productPlaced(@PathVariable(value="id")long id,Model model ) {
    	Addtocart addtocart=addtocartService.getAddtocartById(id);
    	model.addAttribute("addtocart", addtocart);
    	return "placeorder";
    }
    
	
}
