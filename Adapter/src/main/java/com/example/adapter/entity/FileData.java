package com.example.adapter.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileData {


    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;


}
