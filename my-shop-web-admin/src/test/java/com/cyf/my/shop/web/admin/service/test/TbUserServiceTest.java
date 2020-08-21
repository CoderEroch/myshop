package com.cyf.my.shop.web.admin.service.test;

import com.cyf.my.shop.domain.TbUser;
import com.cyf.my.shop.web.admin.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;


import java.util.Date;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})

public class TbUserServiceTest {
    @Autowired
    private TbUserService tbUserService;
    @Test
    public void testSelectAll() {
        List<TbUser> tbUsers = tbUserService.selectAll();
        for (TbUser tbUser : tbUsers) {
            System.out.println(tbUser.getUsername());
        }
    }
    @Test
    public void testInsert(){
        TbUser tbUser=new TbUser();
        tbUser.setUsername("chenyuefei1585");
        tbUser.setPhone("1517385841585");
        tbUser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setEmail("289486995851@qq.com");
        tbUserService.save(tbUser);
    }
    @Test
    public void testDelete(){
        tbUserService.delete(56L);
    }
    @Test
    public void testGetById(){
        TbUser tbUser = tbUserService.getById(16L);
        System.out.println(tbUser.getUsername());
    }
    @Test
    public void testUpdate(){
        TbUser tbUser = tbUserService.getById(57L);
        tbUser.setUsername("chenyuefei");
        tbUserService.update(tbUser);
    }
    @Test
    public void testSelectByUsername(){
        List<TbUser> tbUsers=tbUserService.selectByUsername("uni");
        for (TbUser tbUser : tbUsers) {
            System.out.println(tbUser.getUsername());
        }
    }
}
