package cn.indi.service.impl;

import cn.indi.dao.IOrdersDao;
import cn.indi.domain.Orders;
import cn.indi.service.IOrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrdersDao ordersDao;

//    @Override
//    public List<Orders> findByName(int page, int size) throws Exception {
//        //参数pageNum 是页码值   参数pageSize 代表是每页显示条数
//        PageHelper.startPage(page, size);
//
//       return ordersDao.findByName();
//    }

    @Override
    public List <Orders> findAll(int page,int size) throws Exception {
        //参数page是页码值，参数pageSize代表是每页显示条数
        PageHelper.startPage(page, size);
        return ordersDao.findAll();
    }


    @Override
    public Orders findById(int ordersId) throws Exception {
        return ordersDao.findById(ordersId);
    }
}
