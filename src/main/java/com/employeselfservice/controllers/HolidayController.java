package com.employeselfservice.controllers;

import com.employeselfservice.models.Holiday;
import com.employeselfservice.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping("/user/holiday/getAll")
    public List<Holiday> getAllHolidays(){
        return holidayService.findAllHolidays();
    }
}
