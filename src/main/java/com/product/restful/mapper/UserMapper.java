package com.product.restful.mapper;

import com.product.restful.dto.user.UserDTO;
import com.product.restful.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserDTO mapFromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(roleMapper.mapFromRoleSet(user.getRoles()))
                .build();
    }

    public List<UserDTO> mapFromUserList(List<User> userList) {
        return userList.stream()
                .map(this::mapFromUser)
                .collect(Collectors.toList());
    }
}
