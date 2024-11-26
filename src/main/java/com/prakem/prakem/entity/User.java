package com.prakem.prakem.entity;

import com.mongodb.lang.NonNull;
import com.prakem.prakem.util.Encrypter;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.NoSuchAlgorithmException;
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
    private String salt;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public User(@NonNull String email, @NonNull String password, @NonNull String fullname, byte[] photo, Boolean enabled){
        this.email = email;
        this.fullname = fullname;
        this.photo = photo;
        this.enabled = enabled;
        setPassword(password);
    }

    public void setPassword(String password) {
        this.salt = Encrypter.generateSalt();
        try {
            this.password = Encrypter.hashPassword(password, this.salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while setting password", e);
        }
    }

    public boolean verifyPassword(String password) {
        try {
            return Encrypter.verifyPassword(password, this.salt, this.password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }
}
