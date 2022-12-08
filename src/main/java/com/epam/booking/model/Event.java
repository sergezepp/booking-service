package com.epam.booking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" )
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DATE")
    private Date date;

    public Event(Long id){
        this.id = id ;
    }

}
