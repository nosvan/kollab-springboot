package com.kollab.dto.list;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListDto implements Serializable {
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Long ownerId;
    @NotNull
    private String passcode;
    private Date createdAt;
}
