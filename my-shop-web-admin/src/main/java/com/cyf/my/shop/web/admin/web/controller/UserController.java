package com.cyf.my.shop.web.admin.web.controller;

import com.cyf.my.shop.commons.dto.BaseResult;
import com.cyf.my.shop.domain.TbUser;
import com.cyf.my.shop.web.admin.service.TbUserService;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  用户管理
 */
@Controller
//@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private TbUserService tbUserService;

    @ModelAttribute
    public TbUser getTbUser(Long id){
        TbUser tbUser=null;
        if(id!=null){
            tbUser=tbUserService.getById(id);
        }
        else {
            tbUser=new TbUser();
        }
        return tbUser;
    }
    /**
     * 跳转用户列表
     * @param model
     * @return
     */
    @RequestMapping(value = "userlist",method = RequestMethod.GET)
    public String list(Model model){
        List<TbUser> tbUsers=tbUserService.selectAll();
        model.addAttribute("tbUsers",tbUsers);
        return "userlist";
    }
    /**
     * 跳转用户表单
     * @return
     */
    @RequestMapping(value = "userfrom",method = RequestMethod.GET)
    public  String from(){
        return "userfrom";
    }

    /**
     *  接收表单信息并验证保存
     * @param tbUser
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "usersave",method = RequestMethod.POST)
    public String save(TbUser tbUser ,Model model,RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbUserService.save(tbUser);
        //保存成功
        if(baseResult.getStatus()==200){
            redirectAttributes.addFlashAttribute("baseResult",baseResult);
            return "redirect:/userlist ";
        }
        //保存失败
        else {
            model.addAttribute("baseResult",baseResult);
            return "userfrom";
        }
    }

    /**
     * 搜索
     * @param tbUser
     * @param model
     * @return
     */
    @RequestMapping(value = "usersearch",method = RequestMethod.POST)
    public String search(TbUser tbUser,Model model){
        List<TbUser> tbUsers = tbUserService.search(tbUser);
        model.addAttribute("tbUsers",tbUsers);
        return "userlist";
    }

    /**
     *  删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userdelete",method = RequestMethod.POST)
    public BaseResult delete(String ids){
        BaseResult baseResult=null;
        if(StringUtils.isNotBlank(ids)){
            String[]  idArray=ids.split(",");
            tbUserService.deleteMulti(idArray);
            baseResult=BaseResult.success("删除用户成功");
        }
        else{
            baseResult=BaseResult.fail("删除用户失败");
        }
        System.out.println(ids);
        return baseResult;
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userpage",method = RequestMethod.GET)
    public Map<String,Object> page(HttpServletRequest request){
        Map<String,Object> result=new HashMap<>();
        String strDraw=request.getParameter("draw");
        String strStart=request.getParameter("start");
        String strLength=request.getParameter("length");

        int draw=strDraw==null?0:Integer.parseInt(strDraw);
        int start=strStart==null?0:Integer.parseInt(strStart);
        int length=strLength==null?10:Integer.parseInt(strLength);
        List<TbUser> tbUsers=tbUserService.page(start,length);
        //封装DataTables 需要的结果
        int count=tbUserService.count();
        result.put("draw",draw);
        result.put("recordsTotal",count);
        result.put("recordsFiltered",count);
        result.put("data",tbUsers);
        result.put("error","");
        return result;
    }
}
