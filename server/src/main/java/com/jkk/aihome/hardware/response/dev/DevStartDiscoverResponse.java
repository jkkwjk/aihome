package com.jkk.aihome.hardware.response.dev;

import lombok.Data;

@Data
public class DevStartDiscoverResponse extends DevMqResponse {
    public DevStartDiscoverResponse() {
        super();
        super.setId("1");
        super.setProtocol(1);
    }
}
