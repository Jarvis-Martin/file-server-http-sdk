package com.example.server.dao;

import com.example.server.domain.UploadedFile;

public interface UploadedFileMapper {
    UploadedFile findByName(String filename);

    void insertOne(UploadedFile file);
}