package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

  @NotBlank
  private String oldPassword;

  // 중복코드 제거: Service 계층의 벨리데이션 -> DTO field Validation 변경
  @NotBlank
  @Size(min = 8, message = "새 비밀번호는 8자 이상이어야 합니다.")
  @Pattern(regexp = ".*\\d.*", message = "새 비밀번호는 숫자를 포함해야 합니다.")
  @Pattern(regexp = ".*[A-Z].*", message = "새 비밀번호는 대문자를 포함해야 합니다.")
  private String newPassword;
}
