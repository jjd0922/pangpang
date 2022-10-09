package com.newper.repository;

import com.newper.entity.Goods;
import com.newper.entity.OrderGs;
import com.newper.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersGsRepo extends JpaRepository<OrderGs, Long> {
    public OrderGs findByGoods(Goods goods);

    OrderGs findTopByGoodsOrderByOgIdx(Goods goods);

    @EntityGraph(attributePaths = {"orderGsGroup.orders.orderAddress", "shopProductOption", "companyDelivery", "coupon"})
    public OrderGs findByOgIdx(Long ogIdx);
}
