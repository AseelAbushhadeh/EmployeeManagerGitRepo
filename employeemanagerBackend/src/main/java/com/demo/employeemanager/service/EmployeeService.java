package com.demo.employeemanager.service;

import com.demo.employeemanager.exception.EmployeeNotFoundException;
import com.demo.employeemanager.model.Employee;
import com.demo.employeemanager.repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee){
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees(){
    return employeeRepo.findAll();
    }

    @Transactional
    public Employee updateEmployee2(long id, Employee employee) throws IllegalAccessException {

        Employee employeeById = employeeRepo
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee with ID " + id + " was not found"));

        for (Field f:Employee.class.getDeclaredFields()
             ) {
            f.setAccessible(true);
            if (f.getType().isInstance(new String())){

                if(f.get(employee) !=null){
                String s=f.get(employee).toString();
                if (!s.isEmpty() )
                {
                    f.set(employeeById,s);
                }}
            }
        }
        return employeeById;
    }

    @Transactional
    public Employee updateEmployee(long id, Employee employee) {

        Employee employeeById = employeeRepo
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee with ID " + id + " was not found"));


        if(employee.getName()!=null && employee.getName()!="")
        {
            employeeById.setName(employee.getName());
        }
        if(employee.getEmail()!=null && employee.getEmail()!="")
        {
            employeeById.setEmail(employee.getEmail());
        }
        if(employee.getJobTitle()!=null && employee.getJobTitle()!="")
        {
            employeeById.setJobTitle(employee.getJobTitle());
        }
        if(employee.getPhone()!=null && employee.getPhone()!="")
        {
            employeeById.setPhone(employee.getPhone());
        }
        if(employee.getImageUrl()!=null && employee.getImageUrl()!="")
        {
            employeeById.setImageUrl(employee.getImageUrl());
        }
        return employeeById;
    }
    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }

    public Employee findEmployee(Long id){
        Employee employee= employeeRepo.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Employee with id "+id+"does Not exist"));
        return employee;
    }

}
