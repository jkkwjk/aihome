package com.jkk.aihome.hardware.response.dev;

import com.jkk.aihome.hardware.response.IdResponse;
import lombok.Data;

@Data
public class DevMqResponse extends IdResponse {
    private Integer protocol;
}
