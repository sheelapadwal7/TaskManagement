package com.task.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.DTO.TaskResponseDTO;
import com.task.Repository.TaskRepository;
import com.task.enums.SortCriteria;
import com.task.enums.SortDirection;
import com.task.enums.Status;
import com.task.model.Task;
import com.task.service.TaskService;


@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	TaskRepository taskRepository;

	/*
	 * @GetMapping public List<Task> getTasksWithPagination(
	 * 
	 * @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "10") int pageSize) { return
	 * taskService.getAllTaskwithPagination(page, pageSize); }
	 * 
	 * @GetMapping("/tasksort") public List<Task>
	 * getTasksWithPaginationAndSortingByDate(@RequestParam(defaultValue = "1") int
	 * page,
	 * 
	 * @RequestParam(defaultValue = "10") int pageSize) { return
	 * taskService.getAllTasksWithPaginationAndSortingByDate(page, pageSize); }
	 * 
	 * @GetMapping("/tasksortdesc") public List<Task>
	 * getTasksWithPaginationAndSortingByDesc(@RequestParam(defaultValue = "1") int
	 * page,
	 * 
	 * @RequestParam(defaultValue = "10") int pageSize) { return
	 * taskService.getAllTasksWithPaginationAndSortingByDesc(page, pageSize); }
	 */
	
	@PostMapping("/upload")
	public ResponseEntity<TaskResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		List<String> errors = new ArrayList<>();
		String message = "";

		if (!file.getContentType().equals("text/csv")) {
			errors.add("Uploaded file is not a CSV.");
			return ResponseEntity.badRequest().body(new TaskResponseDTO(false, message, errors));
		}

		// Save the uploaded file to the assets folder
		String filename = "tasks" + ".csv";
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get("src/main/resources/static/assets/" + filename);
			Files.write(path, bytes);
		} catch (IOException e) {
			errors.add("Failed to save uploaded file.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new TaskResponseDTO(false, message, errors));
		}

		try (BufferedReader reader = new BufferedReader(
				new FileReader("src/main/resources/static/assets/" + filename))) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length != 3) {
					errors.add("Invalid CSV format for line: " + line);
					continue;
				}

				String name = data[0];
				String description = data[1];
				String dateStr = data[2];

				// Validation for name
				if (name == null || name.trim().isEmpty()) {
					errors.add("Name cannot be empty for line: " + line);
					continue;
				}

				// Validation for description
				if (description == null || description.trim().isEmpty()) {
					errors.add("Description cannot be empty for line: " + line);
					continue;
				}

				//validation for completion_date
				LocalDate completionDate;
				try {
					completionDate = LocalDate.parse(dateStr);
				} catch (Exception e) {
					errors.add("Invalid date format for line: " + line);
					continue;
				}

				Task task = new Task();
				task.setName(name);
				task.setDescription(description);
				task.setCompletionDate(completionDate);

				taskRepository.save(task);

			}

			if (!errors.isEmpty()) {
				message = "File uploaded with errors.";
				return ResponseEntity.badRequest().body(new TaskResponseDTO(false, message, errors));
			}

			message = "File uploaded successfully.";
			return ResponseEntity.ok(new TaskResponseDTO(true, message, errors));
		} catch (IOException e) {
			errors.add("Failed to process uploaded file.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new TaskResponseDTO(false, message, errors));
		}
	}

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> downloadFile() {
		List<Task> tasks = taskRepository.findAll();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)))) {
			writer.println("Task Name,Description,Completion Date");
			for (Task task : tasks) {
				writer.println(task.getName() + "," + task.getDescription() + "," + task.getCompletionDate());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=task.csv");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/csv"))
				.body(resource);
	}

	@GetMapping
	public List<Task> getAllTasksWithPaginationAndSorting(@RequestParam(defaultValue = "2") int page,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) SortCriteria sortCriteria,
			@RequestParam(required = false) SortDirection sortDirection) {

		return taskService.getAllTasksWithPaginationAndSorting(page, pageSize, sortCriteria, sortDirection);
	}

	@GetMapping("sbway")
	public Page<Task> getAllTasks(Pageable pageable, @RequestParam(required = false) String search,
			@RequestParam(required = false) String description, @RequestParam(required = false) Status status) {
		return taskService.getAllTasks(pageable, search, description, status);
	}

	@GetMapping("/tasks")
	public Page<Task> getTasksWithPagination1(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return taskService.getAllTasksWithPagination(pageable);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskDetails(@PathVariable Integer taskId) {
		Task task = taskService.getTaskDetails(taskId);
		if (task != null) {
			return ResponseEntity.ok(task);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/addtask")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Task createdTask = taskService.createTask(task);
		return ResponseEntity.ok(createdTask);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {

		boolean deleted = taskService.deleteTask(id);

		if (deleted) {
			return ResponseEntity.ok("Task with ID " + id + " deleted successfully.");
		}

		else

		{

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + id + " not found.");

		}

	}
}
