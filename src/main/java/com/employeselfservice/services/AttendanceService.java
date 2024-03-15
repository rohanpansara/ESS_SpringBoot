package com.employeselfservice.services;

import com.employeselfservice.models.Attendance;
import com.employeselfservice.models.Employee;
import com.employeselfservice.models.PunchIn;
import com.employeselfservice.models.PunchOut;
import com.employeselfservice.repositories.AttendanceRepository;
import com.employeselfservice.repositories.PunchInRepository;
import com.employeselfservice.repositories.PunchOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PunchInRepository punchInRepository;

    @Autowired
    private PunchOutRepository punchOutRepository;

    public Attendance calculateAttendance(Long employeeId, LocalDate date) {
        // Fetch PunchIn and PunchOut records for the employee and date from repositories
        List<PunchIn> punchIns = punchInRepository.findByEmployeeIdAndDate(employeeId, date);
        List<PunchOut> punchOuts = punchOutRepository.findByEmployeeIdAndDate(employeeId, date);

        // Create an instance of Attendance entity
        Attendance attendance = new Attendance();
        attendance.setEmployee(new Employee(employeeId)); // Assuming you have a method to fetch employee by id
        attendance.setDate(date);

        // calculate work hours and set in attendance entity
        attendance.calculateWorkHours(punchIns, punchOuts);

        // save the calculated attendance record
        attendanceRepository.save(attendance);

        return attendance;
    }
}
