package com.shadeien.reactor.netty;

import lombok.Data;
import org.msgpack.annotation.Message;

@Data
@Message
public class UserInfo {
    private String name;
    private int age;
}
