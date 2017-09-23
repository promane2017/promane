package intern;

import intern.user.*;
import intern.task.*;
import intern.project.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class InternApplication implements CommandLineRunner {
    @Autowired
    ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	ProjectService projectService;

    @Override
    public void run(String... strings) throws Exception {
	}

	public static void main(String[] args) {
		SpringApplication.run(InternApplication.class, args);
	}
}