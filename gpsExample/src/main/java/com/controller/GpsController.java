/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import javax.servlet.http.HttpServletRequest;

import com.init.GpsInitiator;
import com.init.Initializer;
import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.simulator.GPSSimulatorEndpoint;
import de.taimos.gpsd4java.types.*;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequestMapping("/gps")
public class GpsController
{
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<TPVObject> read(HttpServletRequest request, ModelMap model) throws IllegalAccessException, NoSuchFieldException
    {
        TPVObject coords = GpsInitiator.lastCoordinates;
        if(coords == null)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<TPVObject>(coords, HttpStatus.OK);
    }
}
