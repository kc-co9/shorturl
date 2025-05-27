package com.co.kc.shortening.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;

public class BcryptPasswordService implements PasswordService {

    private static final BCrypt.Hasher HASHER = BCrypt.withDefaults();
    private static final BCrypt.Verifyer VERIFYER = BCrypt.verifyer();

    @Override
    public UserPassword encrypt(UserRawPassword rawPassword) {
        return new UserPassword(HASHER.hashToString(12, rawPassword.getRawPassword().toCharArray()));
    }

    @Override
    public boolean verify(UserRawPassword rawPassword, UserPassword password) {
        return VERIFYER.verify(rawPassword.getRawPassword().toCharArray(), password.getPassword()).verified;
    }

}
