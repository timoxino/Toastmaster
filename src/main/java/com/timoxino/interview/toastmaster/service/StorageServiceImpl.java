package com.timoxino.interview.toastmaster.service;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("gs://interview_cv")
    private Resource cvBucket;

    public void writeCvFile(String fileName, String fileContent) throws IOException {
        Resource file = cvBucket.createRelative(fileName);
        try (OutputStream os = ((WritableResource) file).getOutputStream()) {
            os.write(fileContent.getBytes());
        }
    }
}
