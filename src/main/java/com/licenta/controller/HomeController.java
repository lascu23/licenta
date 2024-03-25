package com.licenta.controller;

import com.licenta.entity.HospitalProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {
    @GetMapping(value = {"/","","/home"})
    public String getHomePage(Model model){
        return "index";
    }

    //asa poti verifica ce rol are utilziatorul logat
//    @ResponseBody
//    @GetMapping("/role")
//    public String getCurrentUserRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null) {
//            for (GrantedAuthority authority : authentication.getAuthorities()) {
//                return authority.getAuthority(); // Returns the role
//            }
//        }
//        return null;
//    }
}
