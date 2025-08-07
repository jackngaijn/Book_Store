package com.example.demo.dto;

import com.example.demo.model.Shelf;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor  
public class ShelfDTO {

    private Long id;
    private LocalDateTime lastAccessDate;
    private int numberOfAccesses;
    private Long bookId;
    private Long appUserId;

    public static ShelfDTO toShelfDTO(Shelf shelf) {
        ShelfDTO shelfDTO = new ShelfDTO();
        shelfDTO.setId(shelf.getId());
        shelfDTO.setLastAccessDate(shelf.getLastAccessDate());
        shelfDTO.setNumberOfAccesses(shelf.getNumberOfAccesses());
        shelfDTO.setBookId(shelf.getBook().getId());
        shelfDTO.setAppUserId(shelf.getAppUser().getId());
        return shelfDTO;
    }
}
