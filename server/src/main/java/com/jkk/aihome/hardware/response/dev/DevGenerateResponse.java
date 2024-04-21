package com.jkk.aihome.hardware.response.dev;

import lombok.Data;

@Data
public class DevGenerateResponse extends DevMqResponse {
    private String devId;

    public DevGenerateResponse(String mac, String devId) {
        super();
        super.setId(mac);
        super.setProtocol(2);
        this.devId = devId;
    }
}
