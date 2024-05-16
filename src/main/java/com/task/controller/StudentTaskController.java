package com.task.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @PostMapping("/update/{id}")
    public String updateTaskStatus(@PathVariable Integer id,
                                   @RequestParam(name= "myfile") MultipartFile file,
                                   @RequestParam Status status) {

        // Validate file type
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            return "Error: File type must be JPEG or PNG.";
        }

        // Validate task ID and retrieve data from database
        StudentTaskDTO studentTaskDTO = taskService.getStudentTaskDTOById(id); 
        if (studentTaskDTO == null) {
            return "Error: Task with ID " + id + " not found.";
        }

        // Define base URL for the assets folder
        String baseUrl = "http://localhost:8080/assets/";

        try {
            // Define assets folder path
            String assetsFolderPath = "D:\\MyProject1\\TaskManagement\\src\\main\\resources\\static\\assets\\"; 

            // Save file to the assets folder with a generated name
            String fileName = generateFileName(file.getOriginalFilename());
            String filePath = assetsFolderPath + fileName; // Construct the file path
            File dest = new File(filePath);
            file.transferTo(dest);

            // Update task status and file path in the DTO
            studentTaskDTO.setStatus(status);
            studentTaskDTO.setFilePath(baseUrl + fileName); // Save only the file path in the DTO
            taskService.updateStudentTaskDTO(studentTaskDTO);

            return "Task updated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while saving the file.";
        }
    }

    // Method to generate a unique file name
    private String generateFileName(String originalFileName) {
        // Implement your logic to generate a unique file name here
        return "image" + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + originalFileName.substring(originalFileName.lastIndexOf('.'));
    }

}
