package com.shadeien.webflux.jpa.repository;

import com.shadeien.webflux.jpa.DeviceDto;
import com.shadeien.webflux.jpa.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xuqinghua on 2017/6/25.
 */
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {

    public DeviceInfo findByUserIdAndClientSnAndDeviceTypeAndVersion(long userId, String clientSn, String deviceType, String version);

//    @Query("select new com.shadeien.webflux.jpa.DeviceDto(id, userId) from #{#entityName} where userId = :userId")
//    public Object findByUserId1(@Param("userId") long userId);

    public DeviceDto findByUserId(long userId);
}
