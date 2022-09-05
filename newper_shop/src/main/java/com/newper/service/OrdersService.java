package com.newper.service;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Orders;
import com.newper.entity.Payment;
import com.newper.repository.CustomerRepo;
import com.newper.repository.OrdersRepo;
import com.newper.repository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final CustomerRepo customerRepo;
    private final OrdersRepo ordersRepo;
    private final PaymentRepo paymentRepo;
    @Autowired
    private ShopSession shopSession;

    /** insert order*/
    @Transactional
    public void insertOrder(ParamMap paramMap){
        Orders orders = Orders.builder()
                .customer(customerRepo.getReferenceById(shopSession.getIdx()))
                .build();

        Payment payment = Payment.builder()
                .payMethod("test")
                .build();

        orders.setPayment(payment);

        paymentRepo.save(payment);


    }
}
