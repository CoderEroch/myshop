package com.cyf.my.shop.web.admin.dao;

import com.cyf.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbUserDao {
    /**
     * 查询
     * @return
     */
    public List<TbUser> selectAll();

    /**
     *
     * @param tbUser
     */
    public void insert(TbUser tbUser);

    /**
     * 删除
     * @param id
     */
    public void delete(Long id);

    /**
     * 单条查询
     * @param id
     */
    public TbUser getById(Long id);

    /***
     * 更新数据
     * @param tbUser
     */
    public void update(TbUser tbUser);

    /**
     * 根据用户名模糊查询
     * @param username
     * @return
     */
    public List<TbUser> selectByUsername(String username);

    /**
     *  根据Email查询用户信息
     *
     * @param email
     * @return
     */
    public TbUser selectByEmail(String email);

    /**
     * 搜索
     * @param tbUser
     * @return
     */
    public List<TbUser> search(TbUser tbUser);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param param
     */
    public List<TbUser> page(Map<String,Object> param);

    /**
     * 查询数据条数
     * @return
     */
    int count();
}
