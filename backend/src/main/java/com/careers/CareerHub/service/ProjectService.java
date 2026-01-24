package com.careers.CareerHub.service;

import com.careers.CareerHub.dto.ProjectDto;
import com.careers.CareerHub.entity.Project;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.ProjectRepository;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project create(ProjectDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    public Page<Project> getUserProjects(
            String email,
            int page,
            int size,
            String sortBy,
            String dir
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sort sort = "desc".equalsIgnoreCase(dir)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return projectRepository.findByOwner(user, pageable);
    }
}
