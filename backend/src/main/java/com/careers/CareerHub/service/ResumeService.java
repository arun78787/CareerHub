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
import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    //add a new dependency at top of ResumeService
    private final ResumeAiService resumeAiService; // constructor-injected


    @Transactional
    public Resume uploadResume(
            MultipartFile file,
            String title,
            String userEmail
    ) throws Exception { // throws Exception because S3Service.uploadFile can throw
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();

        // Upload to S3 and get the object key
        String key = s3Service.uploadFile(file);

        Resume resume = resumeRepository
                .findByUser(user)
                .orElse(new Resume());

        resume.setTitle(title);
        resume.setFileUrl(key);                 // store S3 key (not a local path)
        resume.setFileType(file.getContentType());
        resume.setFileSize(file.getSize());
        resume.setUser(user);
        //save first
        Resume saved = resumeRepository.save(resume);
        //trigger async review (non blocking)
        try{
            resumeAiService.reviewResumeAsync(saved.getId());
        } catch(Exception e){
            // intentionally ignored
            // AI failure must NOT break resume upload
        }

        return saved;
    }

    public Resume getMyResume(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return resumeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No resume uploaded"));
    }

    /**
     * Returns a presigned URL for the given S3 key, valid for 15 minutes.
     */
    public URL getSignedUrl(String key) {
        return s3Service.getPresignedUrl(key, Duration.ofMinutes(15));
    }
}
