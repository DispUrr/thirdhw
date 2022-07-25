package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class UserRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long id;
    private String password;
    private String phoneNumber;
    private Long status;
}
