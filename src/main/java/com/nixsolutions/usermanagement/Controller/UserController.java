package com.nixsolutions.usermanagement.Controller;


import com.nixsolutions.usermanagement.User;
import com.nixsolutions.usermanagement.db.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/browse.html")
    public ModelAndView browse() throws Exception{
        ModelAndView mav = new ModelAndView();
        mav.setViewName("browse");
        List<User> users = new ArrayList<User>();
        mav.addObject(users);
        return mav;
    }

    @RequestMapping(value = "/add.html",method = RequestMethod.GET)
    public String add(Model model)throws Exception{
        model.addAttribute(new User());
        return "add";
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public  String save (User  user) throws Exception{
        userDao.create(user);
        return "redirect:browse.html";
    }



    @InitBinder
    public void  initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), false)); }

 }
