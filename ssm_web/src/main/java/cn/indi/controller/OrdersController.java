package cn.indi.controller;

import cn.indi.domain.Orders;
import cn.indi.service.IOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrderService ordersService;
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = ordersService.findAll(page, size);
        //PageInfo就是一个分页Bean
        PageInfo <? extends Orders> pageInfo=new PageInfo <>(ordersList);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }
    //查询全部订单，未分页
//    @RequestMapping("/findByName.do")
//    public ModelAndView findByName() throws Exception {
//        ModelAndView mv = new ModelAndView();
//        List <Orders> ordersList = ordersService.findByName();
//        mv.addObject("ordersList",ordersList);
//        mv.setViewName("orders-list");
//        return mv;
//    }
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)int ordersId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }

}
