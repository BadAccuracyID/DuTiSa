package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;

@Data
public class LoginData {

    private final String traineeNumber;
    private final String hashedPassword;

}
