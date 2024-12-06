package com.prakem.prakem.entity;

import com.mongodb.lang.NonNull;
import com.prakem.prakem.enumerator.Role;
import com.prakem.prakem.util.Encrypter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
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

    private Integer loginTries = 0;
    private Boolean loginBlocked = false;

    private String salt;

    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public User(@NonNull String email, @NonNull String password, @NonNull String fullname, byte[] photo, Boolean enabled, Set<Role> roles){
        this.email = email;
        this.fullname = fullname;
        this.photo = photo;
        this.enabled = enabled;
        ensureUserRole(roles);
        setPassword(password);
    }

    private void setPassword(String password) {
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

    private void ensureUserRole(Set<Role> roles) {
        if (roles == null) {
            roles = new HashSet<>();
        }

        if (!roles.contains(Role.USER)) {
            roles.add(Role.USER);
        }

        this.roles = new HashSet<>(roles);
    }
}
