package com.shadeien.webflux.jpa.repository;

import com.shadeien.webflux.jpa.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xuqinghua on 2017/6/25.
 */
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {

    public DeviceInfo findByUserIdAndClientSnAndDeviceTypeAndVersion(long userId, String clientSn, String deviceType, String version);
}
