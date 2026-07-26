package com.careforever.dto;

import lombok.Data;

@Data
public class DeviceDto {

    private Long id;
    private String name;
    private String uniqueId;
    private String status;
    private Long positionId;

}
