package com.task.model.specs;

import org.springframework.data.jpa.domain.Specification;

import com.task.enums.Status;
import com.task.model.Task;

public class TaskSpecs {
	
	
	public static Specification<Task> name(String name) {
		return (root, query, builder) -> {
			
			return builder.like(root.get("name"), "%" + name + "%");
		};
	}

	public static Specification<Task> description(String description) {
		return (root, query, builder) -> {
			System.out.println("description " + description);
			return builder.like(root.get("description"), "%" + description + "%");
		};
	}
	
	
	public static Specification<Task> status(Status status) {
		return (root, query, builder) -> {
			System.out.println("status " + status);
			return builder.like(root.get("status"), "%" + status + "%");
		};
	}
}
