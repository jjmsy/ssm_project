package cn.indi.dao;

import cn.indi.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITravellerDao {
    /**
     *
     * @param ordersId 根据当前的ordersId查询
     * @return
     * @throws Exception
     */
    @Select("select * from traveller where id in (select travellerId from order_traveller where orderId = #{ordersId})")
    List<Traveller> findByOrdersId(int ordersId) throws Exception;

}
