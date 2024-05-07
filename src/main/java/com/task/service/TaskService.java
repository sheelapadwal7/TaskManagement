package com.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.DTO.StudentTaskDTO;
import com.task.Repository.AdminRepository;
import com.task.Repository.StudentRepository;
import com.task.Repository.TaskRepository;
import com.task.enums.Status;
import com.task.model.Task;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AdminRepository adminRepository;
	
	
	public List<Task> getAllTaskwithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return taskRepository.findAllTasksWithPagination(offset, pageSize);
    }
	
	public List<Task> getAllTasksWithPaginationAndSortingByDate(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return taskRepository.findAllTasksWithPaginationAndSorting(offset, pageSize);
           
    }
	
	public List<Task> getAllTasksWithPaginationAndSortingByDesc(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return taskRepository.findAllTasksWithPaginationAndSortingByDesc(offset, pageSize);
    }
	
	
	public Page<Task> getAllTasksWithPagination(Pageable pageable) {
        return taskRepository.findAllTasksWithPagination(pageable);
    }
	
	

	public Task createTask(Task task) {

		return taskRepository.save(task);
	}
	
	

	
	
	public boolean startTask(Integer taskId) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setStatus(Status.INPROGRESS);
			taskRepository.save(task);
			return true;
		} else {
			return false;
		}
	}

	public boolean statusUpdateTask(Integer taskId,Task task) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task1 = optionalTask.get();
			task1.setStatus(Status.COMPLETED);
			task1.setInCompletionReason(task.getInCompletionReason());
			taskRepository.save(task1);
			return true;
		} else {
			return false;
		}
	}

	
	public List<StudentTaskDTO> findTaskWithStudentTasks(Integer taskId) {
        return taskRepository.findTaskWithStudentTasksJQL(taskId);
    }

	public List<StudentTaskDTO> findTaskWithStudentTasks2(Integer taskId) {
        List<Object[]> stObject = taskRepository.findTaskWithStudentTasks(taskId);
        List<StudentTaskDTO> stDtos = new ArrayList<>();
        
        for(Object[] st: stObject){
        	StudentTaskDTO stDto = new StudentTaskDTO();
        	stDto.setTaskId((Integer) st[0]);
        	stDto.setTaskName((String) st[1]);
        	stDto.setTaskDescription((String) st[1]);
        	stDto.setStudentTaskId((Integer) st[3]);
        	
        	stDtos.add(stDto);
        }
        
        return stDtos;
    }
	
	public List<Task> getTask() {
		return taskRepository.findAll();
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task getTaskDetails(Integer taskId) {
		return taskRepository.findById(taskId).orElse(null);
	}

	public Optional<Task> getTaskbyId(Integer id) {
		return taskRepository.findById(id);
	}

	public Task addTask(Task task) {

		return taskRepository.save(task);
	}

	public Task updateTask(Integer id, Task task) {
		return taskRepository.save(task);
	}

	public boolean deleteTask(Integer id) {
		boolean exits = taskRepository.existsById(id);
		if (exits) {
			return true;

		}
		return false;
	}


//	public boolean startTask1(Integer taskId) {
//		Optional<Task> optionalTask = taskRepository.findById(taskId);
//		if (optionalTask.isPresent()) {
//			Task task = optionalTask.get();
//			task.setStatus(Status.INPROGRESS);
//			taskRepository.save(task);
//			return true;
//		} else {
//			return false;
//		}
//	}

	public boolean completeTask(Integer taskId) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setStatus(Status.COMPLETED);
			taskRepository.save(task);
			return true;
		} else {
			return false;
		}
	}

	
	
	
	

}
