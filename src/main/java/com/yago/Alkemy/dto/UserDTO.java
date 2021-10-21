package com.yago.Alkemy.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {

    @NotNull(message = "Email cannot be null.")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
            , message = "Invalid email")
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Invalid password. Must have one upper case letter, one lower case letter," +
                    "one number, one special character and at least 8 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
