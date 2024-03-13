package com.employeselfservice.controllers;

import com.employeselfservice.dao.request.PunchRequest;
import com.employeselfservice.dao.response.ApiResponse;
import com.employeselfservice.services.PunchInService;
import com.employeselfservice.services.PunchOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "Requester-Type", exposedHeaders = "X-Get-Header")
public class BaseController {

    @Autowired
    ApiResponse apiResponse;

    @Autowired
    private PunchInService punchInService;

    @Autowired
    private PunchOutService punchOutService;

    @PostMapping("/punch")
    public ResponseEntity<ApiResponse> handlePunch(@RequestBody PunchRequest punchRequest) {
        try {
            if (punchRequest.getPunchType().equals("PunchIn")) {
                LocalTime currentTime = LocalTime.now();
                String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                String punchResponse = punchInService.addPunchIn(punchRequest.getEmployeeId());
                if (punchResponse.equals("punched")) {
                    apiResponse.setSuccess(true);
                    apiResponse.setMessage("Punched In At "+formattedTime);
                } else {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Error Punching In");
                }
            } else if (punchRequest.getPunchType().equals("PunchOut")) {
                LocalTime currentTime = LocalTime.now();
                String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                String punchResponse = punchOutService.addPunchOut(punchRequest.getEmployeeId());
                if (punchResponse.equals("punched")) {
                    apiResponse.setSuccess(true);
                    apiResponse.setMessage("Punched Out At "+formattedTime);
                } else {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Error Punching Out");
                }
            } else {
                // invalid punch type
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Invalid Punch Type");
                return ResponseEntity.badRequest().body(apiResponse);
            }

            // on success response
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}
