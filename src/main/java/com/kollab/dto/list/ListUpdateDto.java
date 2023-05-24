package com.kollab.dto.list;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListUpdateDto {
    @NotNull
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String passcode;
    private Date createdAt;
}
