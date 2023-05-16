package com.kollab.dto.list;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListUpdateDto {
    @NotNull
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String passcode;
    private Date createdAt;
}
