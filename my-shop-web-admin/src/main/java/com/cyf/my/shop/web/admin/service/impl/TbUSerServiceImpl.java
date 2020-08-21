package com.cyf.my.shop.web.admin.service.impl;

import com.cyf.my.shop.commons.dto.BaseResult;
import com.cyf.my.shop.commons.utils.RegexpUtils;
import com.cyf.my.shop.domain.TbUser;
import com.cyf.my.shop.domain.User;
import com.cyf.my.shop.web.admin.dao.TbUserDao;
import com.cyf.my.shop.web.admin.service.TbUserService;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public  class TbUSerServiceImpl implements TbUserService {
    @Autowired
    private TbUserDao tbUserDao;
    @Override
    public List<TbUser> selectAll() {
        return tbUserDao.selectAll();
    }

    @Override
    public BaseResult save(TbUser tbUser) {
        BaseResult baseResult=checkTbUser(tbUser);
        //通过验证
        if(baseResult.getStatus()==BaseResult.STATUS_SUCCESS){
            tbUser.setUpdated(new Date());
            //新增用户
            if(tbUser.getId()==null){
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                tbUser.setCreated(new Date());
                tbUserDao.insert(tbUser);
            }
            //编辑用户
            else {
                tbUserDao.update(tbUser);
            }
            baseResult.setMessage("保存用户成功");
        }
        return baseResult;
    }

    @Override
    public void delete(Long id) {
        tbUserDao.delete(id);
    }

    @Override
    public TbUser getById(Long id) {
        return tbUserDao.getById(id);
    }

    @Override
    public void update(TbUser tbUser) {
        tbUserDao.update(tbUser);
    }

    @Override
    public List<TbUser> selectByUsername(String username) {
        return tbUserDao.selectByUsername(username);
    }

    @Override
    public List<TbUser> search(TbUser tbUser) {
        return tbUserDao.search(tbUser);
    }

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    @Override
    public TbUser login(String email, String password) {
        TbUser tbUser = tbUserDao.selectByEmail(email);
        if(tbUser!=null){
            //明文密码加密
            String md5Password= DigestUtils.md5DigestAsHex(password.getBytes());
            //与数据库获取的密码比较
            if(md5Password.equals(tbUser.getPassword())){
                return tbUser;
            }
        }
        return null;
    }

    /**
     *  批量删除
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        tbUserDao.deleteMulti(ids);
    }

    /**
     * 分页查询
     * @param start
     * @param length
     */
    @Override
    public List<TbUser> page(int start, int length) {
        Map<String,Object> param=new HashMap<>();
        param.put("start",start);
        param.put("length",length);
        return tbUserDao.page(param);
    }

    @Override
    public int count() {
        return tbUserDao.count();
    }

    /**
     * TbUser用户信息的有效性验证
     * @param tbUser
     */
    public BaseResult checkTbUser(TbUser tbUser){
        BaseResult baseResult =BaseResult.success();
        System.out.println(tbUser.getUsername());
        if(StringUtils.isBlank(tbUser.getEmail())){
            baseResult=baseResult.fail(" 邮箱不能为空,请重新输入");
        }
        else if(!RegexpUtils.checkEmail(tbUser.getEmail())){
            baseResult=baseResult.fail(" 邮箱格式不正确,请重新输入");
        }
        else if(StringUtils.isBlank(tbUser.getPassword())){
            baseResult=baseResult.fail(" 密码不能为空,请重新输入");
        }
        else if(StringUtils.isBlank(tbUser.getUsername())){
            baseResult=baseResult.fail(" 姓名不能为空,请重新输入");
        }
        else if(StringUtils.isBlank(tbUser.getPhone())){
            baseResult=baseResult.fail(" 电话号码不能为空,请重新输入");
        }
        else if(!RegexpUtils.checkPhone(tbUser.getPhone())){
            baseResult=baseResult.fail(" 电话号码格式不正确,请重新输入");
        }
        return baseResult;
    }
}
