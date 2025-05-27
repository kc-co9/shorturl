package com.co.kc.shortening.application.assembler;

import com.co.kc.shortening.application.model.client.SessionDTO;
import com.co.kc.shortening.user.domain.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Session组装器
 *
 * @author kc
 */
public class SessionDtoAssembler {

    private SessionDtoAssembler() {
    }

    public static SessionDTO userToDTO(User user, List<Role> roleList, List<Permission> permissionList) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setUserId(user.getUserId().getId());
        sessionDTO.setUserName(user.getName().getName());
        sessionDTO.setUserEmail(user.getEmail().getEmail());
        sessionDTO.setRoleIdList(
                roleList.stream().map(Role::getRoleId).map(RoleId::getId).collect(Collectors.toList()));
        sessionDTO.setPermissionIdList(
                permissionList.stream().map(Permission::getPermissionId).map(PermissionId::getId).collect(Collectors.toList()));
        sessionDTO.setPermissionValueList(
                permissionList.stream().map(Permission::getPermissionValue).map(PermissionValue::getValue).collect(Collectors.toList()));
        return sessionDTO;
    }
}
