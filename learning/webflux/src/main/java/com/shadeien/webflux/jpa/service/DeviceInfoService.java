package com.shadeien.webflux.jpa.service;

import com.shadeien.webflux.jpa.entity.DeviceInfo;
import com.shadeien.webflux.jpa.repository.DeviceInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2017/6/22.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class DeviceInfoService {

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

//    public void find() {
//        deviceInfoRepository.findAllById()
//    }

    @Transactional(readOnly = false)
    public void saveNewDevice() {
        log.info("start");
        saveNewDevice1();
        saveNewDevice2();
    }

    @Transactional(readOnly = false)
    public void saveNewDevice1() {
        log.info("saveNewDevice1");
        DeviceInfo deviceInfo = new DeviceInfo();
//        deviceInfo.setId(111111);
        deviceInfo.setClientSn("111111");
        deviceInfo.setDeviceType("111111");
        deviceInfo.setRemarks("111111");
        deviceInfo.setUserId(ThreadLocalRandom.current().nextInt());
        deviceInfo.setVersion("111111");
        deviceInfoRepository.save(deviceInfo);
    }

    @Transactional(readOnly = false)
    public void saveNewDevice2() {
        log.info("saveNewDevice1");
        DeviceInfo deviceInfo = new DeviceInfo();
//        deviceInfo.setId(222222);
        deviceInfo.setClientSn("222222");
        deviceInfo.setDeviceType("222222");
        deviceInfo.setRemarks("222222");
        deviceInfo.setUserId(222222);
        deviceInfo.setVersion("222222");
        deviceInfoRepository.save(deviceInfo);
        throw new RuntimeException("error");
    }
}
