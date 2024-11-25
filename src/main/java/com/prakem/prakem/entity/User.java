package com.prakem.prakem.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String fullname;

    private byte[] photo;

    private Boolean enabled = true;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public User(@NonNull String email, @NonNull String password, @NonNull String fullname, byte[] photo, Boolean enabled){
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.photo = photo;
        this.enabled = enabled;
    }
}
