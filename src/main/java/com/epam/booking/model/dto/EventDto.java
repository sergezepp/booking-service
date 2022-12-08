package com.epam.booking.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class EventDto  {

    private Long id;

    private String title;

    private Date date;


}
