package com.roudane.preparationentretien.domain.user;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDomain {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
