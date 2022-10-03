package com.newper.repository;

import com.newper.entity.HeaderOrder;
import com.newper.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeaderOrderRepo extends JpaRepository<HeaderOrder,Long> {

    Optional<HeaderOrder> findByShopAndHoRowAndHoCol(Shop shop, int hoRow, int hoCol);
    List<HeaderOrder> findByShop(Shop shop);
    default HeaderOrder[][] HeaderOrderArray(Shop shop){
        HeaderOrder[][] ho = new HeaderOrder[3][3];
        List<HeaderOrder> hoList = findByShop(shop);
        for (HeaderOrder headerOrder : hoList) {
            ho[headerOrder.getHoRow() - 1][headerOrder.getHoCol() - 1] = headerOrder;
        }
        return ho;
    }
}
