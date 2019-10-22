package com.shadeien.webflux.jpa;

import lombok.Data;

@Data
public class DeviceDto {
    long id;
    long userId;
    String clientSn;

    public DeviceDto(long id, long userId, String clientSn) {
        this.id = id;
        this.userId = userId;
        this.clientSn = clientSn;
    }

    void test() {

    }
}
