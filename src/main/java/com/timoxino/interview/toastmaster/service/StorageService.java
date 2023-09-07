package com.timoxino.interview.toastmaster.service;

import java.io.IOException;

public interface StorageService {
    void writeCvFile(String fileName, String fileContent) throws IOException;
}
