package com.careers.CareerHub.service;

import com.careers.CareerHub.dto.ProjectDto;
import com.careers.CareerHub.entity.Project;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.ProjectRepository;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project create(ProjectDto dto, String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();

        Project p = new Project();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setGithubUrl(dto.getGithubUrl());
        p.setLiveDemoUrl(dto.getLiveDemoUrl());
        p.setTechStack(dto.getTechStack());
        p.setPublic(dto.isPublic());
        p.setOwner(user);

        return projectRepository.save(p);
    }
    public List<Project> getUserProjects(String email){
        return ProjectRepository.findAll()
                .stream()
                .filter(p->p.getOwner().getEmail().equals(email))
                .toList();
    }

}
