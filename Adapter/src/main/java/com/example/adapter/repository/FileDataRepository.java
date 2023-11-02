package com.example.adapter.repository;

import com.example.adapter.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository  extends JpaRepository<FileData,Long> {
}
