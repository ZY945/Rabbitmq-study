package com.example.mailspringbootrabbitmq.Bean;

import lombok.Data;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2022/12/12 19:35
 */
@Data
public class WorkOrder {
    String mail;
    String name;
    String phone;
    String arrivalTime;
}
