package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.DTO.StudentTaskDTO;
import com.task.enums.Status;
import com.task.model.Student;
import com.task.model.StudentTask;
import com.task.model.Task;
import com.task.model.TokenLog;
import com.task.service.StudentTaskService;
import com.task.service.TaskService;
import com.task.service.TokenLogService;

import jakarta.servlet.annotation.MultipartConfig;
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
    
//    @RequestPart("user") @Valid User user,
//    @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file
    
    //RequestPart
	public String updateTaskStatus(@PathVariable String id, 
			@RequestParam(name= "myfile") MultipartFile file, 
			@RequestParam Status status) {
		
		// validate static
		// filename make sure jpg ya png
		
		// task id validate and data get
		
		//save base url in properties file http://localhost:8080/assets/
		// file save drive (bg.jpg -> generate name -> 1-16052024.jpg) and path db
		
		return "";
	}    
}
