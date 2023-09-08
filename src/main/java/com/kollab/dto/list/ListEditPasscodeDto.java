package com.kollab.dto.list;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEditPasscodeDto {
    @NotNull
    private Long listId;
    @NotNull
    private String oldPasscode;
    @NotNull
    private String passcode;
}
