package org.example.expert.domain.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.dto.response.UserResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManagerMapper {

  public static ManagerResponse from(Manager manager) {
    UserResponse user = new UserResponse(manager.getUser().getId(), manager.getUser().getEmail());
    return new ManagerResponse(
        manager.getId(),
        user
    );
  }
}
