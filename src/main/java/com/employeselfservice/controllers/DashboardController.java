package com.employeselfservice.controllers;

import com.employeselfservice.JWT.services.JWTService;
import com.employeselfservice.dao.request.LeaveRequest;
import com.employeselfservice.dao.response.ApiResponse;
import com.employeselfservice.models.Employee;
import com.employeselfservice.models.Attendance;
import com.employeselfservice.services.AttendanceService;
import com.employeselfservice.services.EmployeeService;
import com.employeselfservice.services.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requester-Type", exposedHeaders = "X-Get-Header")
public class DashboardController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApiResponse apiResponse;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private AttendanceService attendanceService;


    @GetMapping("/admin/allEmployees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> userProfile() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<Employee> employees = employeeService.findAll();
            apiResponse.setSuccess(true);
            apiResponse.setData(employees);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Error fetching employee profiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/user/currentEmployee")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse> getEmployeeDetails(@RequestParam("id") String employeeId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Employee employee = employeeService.findEmployeeById(Long.parseLong(employeeId));
            if (employee!=null) {
                apiResponse.setSuccess(true);
                apiResponse.setData(employee);
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Employee with ID " + employeeId + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (NumberFormatException e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Invalid employee ID format");
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Error fetching employee details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/user/applyLeave")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse> applyForLeave(@RequestBody LeaveRequest leaveRequest){
        try {
            if (leaveRequest.getLeaveFrom().isAfter(LocalDate.now()) || leaveRequest.getLeaveTo().isEqual(LocalDate.now())) {

                String leaveResponse = leaveService.applyForLeave(leaveRequest);
                if (leaveResponse.equals("applied")) {
                    apiResponse.setSuccess(true);
                    apiResponse.setMessage("Leave Applied Successfully");
                } else {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Error Applying For Leave");
                }
            } else {
                // cannot apply for leave before today
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Cannot Apply For Leave Before "+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

    @PostMapping("/user/attendance")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse> calculateAttendance(@RequestParam long employeeId, @RequestParam String date) {
        try {
            System.out.println(employeeId);
            LocalDate localDate = LocalDate.parse(date);
            Attendance calculatedAttendance = attendanceService.calculateAttendance((Long)employeeId, localDate);
            if (calculatedAttendance != null) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Attendance Fetched");
                apiResponse.setData(calculatedAttendance);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Couldn't Fetch Attendance");
            }
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

}

