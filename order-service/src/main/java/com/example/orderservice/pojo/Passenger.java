package com.example.orderservice.pojo;

import lombok.Data;

@Data
public class Passenger {
    String user_id;
    String password;
    String name;
    String sex;
    Double balance;
}
