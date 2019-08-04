package cn.indi.service;

import cn.indi.domain.Orders;

import java.util.List;

public interface IOrderService {
//    List<Orders> findByName(int page, int size) throws Exception;
        List<Orders> findAll(int page,int size) throws Exception;
    Orders findById(int ordersId) throws Exception;
}
