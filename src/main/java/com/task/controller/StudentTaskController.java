package com.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.StudentTaskDTO;
import com.task.enums.LinkType;
import com.task.model.Student;
import com.task.model.StudentTask;
import com.task.model.Task;
import com.task.model.TokenLog;
import com.task.service.StudentTaskService;
import com.task.service.TaskService;
import com.task.service.TokenLogService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentTaskController {

    @Autowired
    private StudentTaskService studentTaskService;

    @Autowired
    private TokenLogService tokenLogService;
    
    @Autowired
    TaskService taskService;

    @PostMapping("/studenttasks")
    public ResponseEntity<String> createStudentTask(@RequestBody StudentTask studenttask) {
        Student student = studenttask.getStudent();
        Task task = studenttask.getTask();


        // Check if student and task exist
        if (student == null || task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student or Task not found");
        }

        // Save the student task
        studentTaskService.saveStudentTask(student, task);
        return ResponseEntity.status(HttpStatus.CREATED).body("StudentTask created successfully");
    }
    
    @GetMapping("/tasks/studenttasks/{taskId}")
    public ResponseEntity<List<StudentTaskDTO>> getTaskWithStudentTasks(HttpServletRequest request,  @PathVariable Integer taskId
    		){
//    		, @RequestHeader("Authorization") String token) {
//        TokenLog tokenLog = tokenLogService.getTokenLog(token);
    	
    	//passing data from filter to controller
        TokenLog tokenLog = (TokenLog) request.getAttribute("tokenLog");
        
    	
        // ACL
        if(!tokenLogService.isStudent(tokenLog)) {
        	return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        
    	List<StudentTaskDTO> taskWithStudentTasks = taskService.findTaskWithStudentTasks2(taskId);
        
        if (taskWithStudentTasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskWithStudentTasks);
}
}
