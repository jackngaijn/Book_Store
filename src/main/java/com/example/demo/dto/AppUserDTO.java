package com.example.demo.dto;

import com.example.demo.model.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String telephone;
    private String mobile;
    private String address;
    private String status;
    private String createdDate;
    private String updatedDate;
    private String approvedBy;
    private String approvedDate;
    private int numberOfRetries;
    private String lastLoginDate;

    public static AppUserDTO convertToDto(AppUser appUser) {
        AppUserDTO dto = new AppUserDTO();
        dto.setId(appUser.getId());
        dto.setUsername(appUser.getUsername());
        dto.setName(appUser.getName());
        dto.setEmail(appUser.getEmail());
        dto.setTelephone(appUser.getTelephone());
        dto.setMobile(appUser.getMobile());
        dto.setAddress(appUser.getAddress());
        dto.setStatus(appUser.getStatus());
        return dto;
    }
}
