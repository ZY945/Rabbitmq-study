package com.example.rabbitmqspringboot.domin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    int id;
    String name;
}