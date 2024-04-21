package com.jkk.aihome.hardware.response.dev;

import lombok.Data;

@Data
public class DevRemoveResponse extends DevMqResponse {
    public DevRemoveResponse(String devId) {
        super();
        this.setId(devId);
        this.setProtocol(3);
    }
}
