package com.example.rpc.demo.comsumer.service;

import com.example.rpc.demo.provider.service.Calculator;
import com.example.rpc.demo.reuqest.CalculateRpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CalculatorRemoteImpl implements Calculator {

    public static final int PORT = 9999;

    public int plus(int a, int b) {
        try {
            Socket socket = new Socket("127.0.0.1", PORT);

            // 将请求参数序列化
            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // 发送请求发给服务提供方
            objectOutputStream.writeObject(calculateRpcRequest);

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();

            if (response instanceof Integer) {
                return (Integer) response;
            } else {
                throw new InternalError();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError();
        }
    }

    private CalculateRpcRequest generateRequest(int a, int b) {
        CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("plus");
        return calculateRpcRequest;
    }
}
