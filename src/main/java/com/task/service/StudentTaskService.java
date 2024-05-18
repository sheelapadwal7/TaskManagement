package com.task.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Repository.StudentTaskRepository;
import com.task.model.Student;
import com.task.model.StudentTask;
import com.task.model.Task;

@Service
public class StudentTaskService {

    @Autowired
    private StudentTaskRepository studentTaskRepository;
    public String generateFileName(String originalFileName) {
        
        return "image" + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + originalFileName.substring(originalFileName.lastIndexOf('.'));
    }

   
    public void saveStudentTask(Student student, Task task) {
        StudentTask studentTask = new StudentTask();
        studentTask.setStudent(student);
        studentTask.setTask(task);
        studentTaskRepository.save(studentTask);
    }
    
    
    public void updateTask(StudentTask studentTask)
    {
    	studentTaskRepository.save(studentTask);
    }
    
}



