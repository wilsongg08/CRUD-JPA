package com.ejemplo.crudoperation.controller;

import com.ejemplo.crudoperation.model.Employee;
import com.ejemplo.crudoperation.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee){
        try{
            repository.save(employee);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Not inserted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Succesfully inserted", HttpStatus.OK);
    }

    @GetMapping("/select")
    public ResponseEntity<List<Employee>> getEmployee(){
        List<Employee> list= null;
        try{
            list = repository.findAll();
        }
        catch(Exception e){
            return new ResponseEntity<List<Employee>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{eid}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("eid") int eid){
        try{
            repository.deleteById(eid);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Not delete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Succesfully deleted", HttpStatus.OK);
    }

    @PutMapping("/update")
    public String updateEmp(@RequestBody Employee emp){
        Optional<Employee> temp = repository.findById(emp.getId());
        Employee employee = temp.get();
        //Swapping
        employee.setName(emp.getName());
        employee.setSalary(emp.getSalary());
        repository.save(employee);

        return "Succesfully updated";
    }
}
