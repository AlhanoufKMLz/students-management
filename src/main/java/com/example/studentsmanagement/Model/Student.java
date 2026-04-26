package com.example.studentsmanagement.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private int age;
    private double degree;
    private double GPA;
}
