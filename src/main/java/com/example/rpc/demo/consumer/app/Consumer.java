package com.example.rpc.demo.consumer.app;

import com.example.rpc.demo.consumer.service.CalculatorRemoteImpl;
import com.example.rpc.demo.provider.service.Calculator;

public class Consumer {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorRemoteImpl();
        int result = calculator.plus(5, 2);
        System.err.println("5 plus 2 's result is " + result);
    }
}
