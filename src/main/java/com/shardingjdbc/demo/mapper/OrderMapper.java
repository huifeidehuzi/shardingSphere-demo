package com.shardingjdbc.demo.mapper;

import com.shardingjdbc.demo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Select("select id,age,name from test_order where id = #{id}")
    Order getById(@Param("id") Long id);


    @Insert("insert into test_order(age,name) values(#{age},#{name})")
    int insert(Order order);
}
