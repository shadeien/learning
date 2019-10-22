package com.shadeien.reactor.mynetty;

import lombok.Data;

@Data
public final class NettyMessage {
    private Header header;
    private Object body;
}
