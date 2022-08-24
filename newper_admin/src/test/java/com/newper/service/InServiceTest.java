package com.newper.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InServiceTest {

    @Autowired
    InService inService;

    @Test
    void insertInGroup() {
        inService.insertInGroup(2);

    }
}