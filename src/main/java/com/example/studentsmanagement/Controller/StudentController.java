package com.example.studentsmanagement.Controller;

import com.example.studentsmanagement.ApiResponse.ApiResponse;
import com.example.studentsmanagement.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    ArrayList<Student> students = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ApiResponse addStudent(@RequestBody Student newStudent){
        //check id
        for(Student s: students){
            if(s.getId().equalsIgnoreCase(newStudent.getId()))
                return new ApiResponse("The ID: " + newStudent.getId() + " is already used please enter another ID.");
        }
        //check age
        if(newStudent.getAge() <= 0)
            return new ApiResponse("Age must be positive number.");
        //check GPA
        if(newStudent.getGPA() < 0 || newStudent.getGPA() > 5)
            return new ApiResponse("GPA must be between 0 and 5.");

        students.add(newStudent);
        return new ApiResponse("Student added successfully.");
    }

    @GetMapping("/get/all")
    public ArrayList<Student> getAllStudents(){
        return students;
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateStudent(@PathVariable String id, @RequestBody Student newStudent){
        //check age
        if(newStudent.getAge() <= 0)
            return new ApiResponse("Age must be positive number.");
        //check GPA
        if(newStudent.getGPA() < 0 || newStudent.getGPA() > 5)
            return new ApiResponse("GPA must be between 0 and 5.");

        for(int i=0; i < students.size(); i++){
            if(students.get(i).getId().equalsIgnoreCase(id)){
                students.set(i, newStudent);
                return new ApiResponse("Student updated successfully.");
            }
        }
        return new ApiResponse("Student with ID: " + id + " not found.");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteStudent(@PathVariable String id){
        for(int i=0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id)) {
                students.remove(i);
                return new ApiResponse("Student deleted successfully");
            }
        }
        return new ApiResponse("Student with ID: " + id + " not found.");
    }


    //EXTRA ENDPOINTS
    @GetMapping("/get/honor/category")
    public Map<Student, String> categoriesStudents(){
        Map<Student, String> result = new HashMap<>();
        for(Student s: students){
            if(s.getGPA() >= 4.75)
                result.put(s, " * First honor");
            else if(s.getGPA() >= 4.25)
                result.put(s, " * Second honor");
            else
                result.put(s, " * Regular student");
        }
        return result;
    }

    @GetMapping("/get/greater/average")
    public ArrayList<Student> StudentsGreaterThanAverage(){
        if(students.isEmpty())
            return new ArrayList<>();

        double total = 0;
        for(Student s: students){
            total += s.getGPA();
        }
        double average = total / students.size();

        ArrayList<Student> studentsGreaterThanAverage = new ArrayList<>();
        for(Student s: students){
           if(s.getGPA() >= average)
               studentsGreaterThanAverage.add(s);
        }
        return studentsGreaterThanAverage;
    }


}
