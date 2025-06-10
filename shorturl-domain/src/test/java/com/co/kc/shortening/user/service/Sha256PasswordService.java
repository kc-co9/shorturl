package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Sha256PasswordService implements PasswordService {

    @Override
    public UserPassword encrypt(UserRawPassword rawPassword) {
        return new UserPassword(
                Hashing.sha256().hashString(rawPassword.getRawPassword(), StandardCharsets.UTF_8).toString());
    }

    @Override
    public boolean verify(UserRawPassword rawPassword, UserPassword password) {
        String encryptPassword = Hashing.sha256().hashString(rawPassword.getRawPassword(), StandardCharsets.UTF_8).toString();
        return password.getPassword().equals(encryptPassword);
    }

}
