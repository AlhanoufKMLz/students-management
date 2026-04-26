package com.example.studentsmanagement.Controller;

import com.example.studentsmanagement.ApiResponse.ApiResponse;
import com.example.studentsmanagement.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    ArrayList<Student> students = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ApiResponse addStudent(@RequestBody Student newStudent){
        for(Student s: students){
            if(s.getId().equalsIgnoreCase(newStudent.getId()))
                return new ApiResponse("The ID: " + newStudent.getId() + " is already used please enter another ID.");
        }
        students.add(newStudent);
        return new ApiResponse("Student added successfully.");
    }

    @GetMapping("/get/all")
    public ArrayList<Student> getAllStudents(){
        return students;
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateStudent(@PathVariable String id, @RequestBody Student newStudent){
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

    @GetMapping("/get/greater/average")
    public ArrayList<Student> StudentsGreaterThanAverage(){
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
