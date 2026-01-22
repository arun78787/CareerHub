package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.ResumeRepository;
import com.careers.CareerHub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    private static final String UPLOAD_DIR = "uploads/resumes/";

    @Transactional
    public Resume uploadResume(
            MultipartFile file,
            String title,
            String userEmail
    ) throws IOException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        //create directory if not exist
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        Files.write(filePath, file.getBytes());

        Resume resume = resumeRepository
                .findByUser(user)
                .orElse(new Resume());

        resume.setTitle(title);
        resume.setFileUrl(filePath.toString());
        resume.setFileType(file.getContentType());
        resume.setFileSize(file.getSize());
        resume.setUser(user);

        return resumeRepository.save(resume);
    }
    public Resume getMyResume(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return resumeRepository.findByUser(user)
                .orElseThrow(() ->new RuntimeException("No resume uploaded"));
    }
}
