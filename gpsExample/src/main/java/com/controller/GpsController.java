/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/gps")
public class GpsController
{
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<Object> read(HttpServletRequest request, ModelMap model) throws IllegalAccessException, NoSuchFieldException
    {
        return new ResponseEntity<Object>("bla", HttpStatus.OK);
    }
}
