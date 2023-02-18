package com.demo.employeemanager.controller;

import com.demo.employeemanager.model.Employee;
import com.demo.employeemanager.service.EmployeeService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> listAll()
    {
        List<Employee> list= employeeService.findAllEmployees();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") long id)
    {
        Employee employee= employeeService.findEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> findEmployee(@RequestBody Employee employee)
    {
        Employee addedEmployee= employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee)
    {
        Employee addedEmployee= employeeService.updateEmployee2(id, employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable("id") long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
