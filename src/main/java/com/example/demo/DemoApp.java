package com.example.demo;


import org.springframework.stereotype.Controller;

@Controller
public class DemoApp {
    public Double getSquaredPower(Double number) {
        number = number * number;
        return number;
    }
}
