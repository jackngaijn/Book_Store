package com.example.demo.dto;

import com.example.demo.model.Author;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private String country;

    public static AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setCountry(author.getCountry());
        return authorDTO;
    }
}
