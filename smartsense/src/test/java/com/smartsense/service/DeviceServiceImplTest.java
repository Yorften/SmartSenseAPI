package com.smartsense.service;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.device.UpdateDeviceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Test
    void getDeviceById_ShouldThrowException_WhenNotImplemented() {
        assertThrows(UnsupportedOperationException.class,
                () -> deviceService.getDeviceById("1"));
    }

    @Test
    void getAllDevices_ShouldThrowException_WhenNotImplemented() {
        Pageable pageable = PageRequest.of(0, 10);
        assertThrows(UnsupportedOperationException.class,
                () -> deviceService.getAllDevices(pageable, "", ""));
    }

    @Test
    void addDevice_ShouldThrowException_WhenNotImplemented() {
        assertThrows(UnsupportedOperationException.class,
                () -> deviceService.addDevice(new DeviceDTO()));
    }

    @Test
    void updateDevice_ShouldThrowException_WhenNotImplemented() {
        assertThrows(UnsupportedOperationException.class,
                () -> deviceService.updateDevice("1", new UpdateDeviceDTO()));
    }

    @Test
    void deleteDevice_ShouldThrowException_WhenNotImplemented() {
        assertThrows(UnsupportedOperationException.class,
                () -> deviceService.deleteDeviceById("1"));
    }
}