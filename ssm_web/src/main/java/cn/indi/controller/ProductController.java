package cn.indi.controller;

import cn.indi.domain.Product;
import cn.indi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService service;

    /**
     * 查询全部产品
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")//表示只有admin这个角色才可以访问,spring-security提供的注解
    public ModelAndView findAll() throws Exception {
        //查询到的所有信息封装到ModelAndView里面，发送到前台展示
        ModelAndView mv = new ModelAndView();
        List <Product> all = service.findAll();
        mv.addObject("productList", all);
        mv.setViewName("product-list1");
        return mv;
    }
    /**
     *产品添加,返回重新查询
     */
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        service.save(product);
        return "redirect:findByName.do";
    }
}
