package com.cyf.my.shop.web.admin.service;

import com.cyf.my.shop.commons.dto.BaseResult;
import com.cyf.my.shop.domain.TbUser;

import java.util.List;

public interface TbUserService {
    List<TbUser> selectAll();
    BaseResult save(TbUser tbUser);
    void delete(Long id);
    TbUser getById(Long id);
    void update(TbUser tbUser);
    List<TbUser> selectByUsername(String username);

    /**
     * 搜索功能
     * @param tbUser
     * @return
     */
    List<TbUser> search(TbUser tbUser);
    /**
     *  用户登录
     * @param email
     * @param password
     * @return
     */
    TbUser login(String email,String password);

    /**
     * 批量删除
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param start
     * @param length
     */
    List<TbUser> page(int start,int length);

    /**
     * 查询数据总数
     * @return
     */
    int count();

}
