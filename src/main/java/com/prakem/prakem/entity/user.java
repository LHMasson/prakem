package com.prakem.prakem.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // Gera getters, setters, equals, hashCode, toString automaticamente
@Document(collection = "user")
public class user {
}
