package com.employeselfservice.services;

import com.employeselfservice.models.Designation;
import com.employeselfservice.models.Employee;
import com.employeselfservice.repositories.DesignationRepository;
import com.employeselfservice.repositories.EmployeeRepository;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DesignationRepository designationRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee userDetail = employeeRepository.findByEmail(email);

        if (userDetail == null) {
            throw new UsernameNotFoundException("User not found " + email);
        }

        return new EmployeeDetails(userDetail);
    }


    public String addUser(Employee employee) throws TransientPropertyValueException {
        employee.setPassword(encoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return "added";
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }

//    public Optional<Employee> findById(Long id){
//            return employeeRepository.findById(id);
//    }

    public Employee findEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

}

