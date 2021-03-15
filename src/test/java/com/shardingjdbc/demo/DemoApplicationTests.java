package com.shardingjdbc.demo;

import com.shardingjdbc.demo.entity.Order;
import com.shardingjdbc.demo.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void select() {
        /*Order order = new Order();
        order.setId(39873L);
        order.setName("王五11112");
        orderMapper.insert(order);*/

        System.out.println(orderMapper.getById(578161069680230400L));

    }

    @Test
    public void insert() {
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setAge(99);
            order.setName("哈哈哈哈哈哈哈" + i);
            orderMapper.insert(order);
        }


    }

}
