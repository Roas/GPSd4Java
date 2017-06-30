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
import java.util.List;

@Controller
public class GpsController
{
    /*
    Get the last GPS coordinate in JSON format
     */
    @ResponseBody
    @RequestMapping(value = "/gps", method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<TPVObject> getLastCoordinate(HttpServletRequest request, ModelMap model) throws IllegalAccessException, NoSuchFieldException
    {
        TPVObject coords = GpsInitiator.lastCoordinates;
        if(coords == null)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<TPVObject>(coords, HttpStatus.OK);
    }

    /*
    Get a list with GPS coordinates (history) in JSON format
     */
    @ResponseBody
    @RequestMapping(value = "/gpslist", method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<List<TPVObject>> getCoordinateList(HttpServletRequest request, ModelMap model) throws IllegalAccessException, NoSuchFieldException
    {
        if(GpsInitiator.coordinateLog.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<TPVObject>>(GpsInitiator.coordinateLog, HttpStatus.OK);
    }

    /*
    Change the gps settings flight and speed
     */
    @ResponseBody
    @RequestMapping(value = "/gpssettings/{flightcode}/{speed}", method = RequestMethod.POST, headers = "Accept=*/*")
    public ResponseEntity<Boolean> changeSettings(@PathVariable("flightcode") String flightcode, @PathVariable("speed") int speed, HttpServletRequest request, ModelMap model) throws IllegalAccessException, NoSuchFieldException
    {
        GpsInitiator.stop();
        GpsInitiator.start(flightcode, GpsInitiator.XML_FILE_LOCATION, speed);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
