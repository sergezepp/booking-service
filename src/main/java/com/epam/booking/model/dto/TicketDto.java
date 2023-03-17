package com.epam.booking.model.dto;

import com.epam.booking.model.Category;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@XmlRootElement
public class TicketDto implements Serializable {

    private Long id;

    private Long eventId;

    private Long userId;

    private Category category;

    private Integer place;

    private String eventTitle;

    private String userIdentifier;

    private Date date;

}
