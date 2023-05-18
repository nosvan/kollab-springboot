package com.kollab.dto.list;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListEditPasscodeDto {
    @NotNull
    private Long listId;
    @NotNull
    private String oldPasscode;
    @NotNull
    private String passcode;
}
