/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController
{
    @RequestMapping(value = "/")
    public ModelAndView index(Model model)
    {
        double latitude = 51.508742;
        double longitude = -0.120850;

        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);

        // Return the index.jsp page
        return new ModelAndView("index");
    }
}
