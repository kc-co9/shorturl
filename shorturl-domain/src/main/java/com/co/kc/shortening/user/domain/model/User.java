package com.co.kc.shortening.user.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import com.co.kc.shortening.user.service.PasswordService;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * 认证用户-聚合根
 *
 * @author kc
 */
@EqualsAndHashCode(callSuper = false)
public class User extends Identification {
    /**
     * 聚合根-唯一标识
     */
    @Getter
    private final UserId userId;
    @Getter
    private UserEmail email;
    @Getter
    private UserName name;
    @Getter
    private UserPassword password;
    @Getter
    private List<RoleId> roleIds;

    public User(UserId userId, UserEmail email, UserName name, UserPassword password, List<RoleId> roleIds) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roleIds = roleIds;
    }

    public void changeEmail(UserEmail email) {
        if (!Objects.equals(this.email, email)) {
            this.email = email;
        }
    }

    public void changeUserName(UserName name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
        }
    }

    public void changePassword(UserPassword password) {
        if (!Objects.equals(this.password, password)) {
            this.password = password;
        }
    }

    public void changeRole(List<RoleId> roleIds) {
        this.roleIds = roleIds;
    }

    public boolean validateRawPassword(UserRawPassword rawPassword, PasswordService passwordService) {
        return passwordService.verify(rawPassword, this.password);
    }

}
