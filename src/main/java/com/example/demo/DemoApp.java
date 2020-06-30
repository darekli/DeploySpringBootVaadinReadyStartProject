package com.example.demo;


import org.springframework.stereotype.Controller;

import java.math.BigInteger;

@Controller
public class DemoApp {

    public Double getSquaredPower(Double number) {
        number = number * number;
        return number;
    }
    public BigInteger getFactorial(Double number){
        if(number>0 && number<=150) {
            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= number; i++)
                result = result.multiply(BigInteger.valueOf(i));
            return (result);

        }
        else {
            return BigInteger.valueOf(0);
        }
    }
}
