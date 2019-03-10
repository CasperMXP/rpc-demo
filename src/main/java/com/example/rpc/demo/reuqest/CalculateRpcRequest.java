package com.example.rpc.demo.reuqest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CalculateRpcRequest implements Serializable {
    private String method;
    private int a;
    private int b;
}
