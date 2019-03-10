package com.example.rpc.demo.provider.app;

import com.example.rpc.demo.provider.service.Calculator;
import com.example.rpc.demo.provider.service.CalculatorImpl;
import com.example.rpc.demo.reuqest.CalculateRpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: maxp 2019年3月10日14:38:14
 */
public class Provider {

    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
        new Provider().run();
    }

    private void run() throws IOException {
        System.err.println("开启服务提供方.....");
        ServerSocket listener = new ServerSocket(9999);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    // 将请求参数反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();

                    System.err.println("请求参数: " + object);

                    // 调用服务
                    int result = 0;
                    if (object instanceof CalculateRpcRequest) {
                        CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
                        if ("plus".equals(calculateRpcRequest.getMethod())) {
                            result = calculator.plus(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    // 返回结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Integer(result));

                    System.err.println("服务提供方执行结果: " + result);

                } catch (Exception e) {
                    System.err.println(e);
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }

}
