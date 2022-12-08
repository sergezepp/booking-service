package com.epam.booking.model.dto;

import com.epam.booking.model.Category;
import lombok.Data;

@Data
public class TicketDto {

    private Long id;

    private Long eventId;

    private Long userId;

    private Category category;

    private Integer place;

}
