package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking_details")
public class BookingDetail extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "num_adults")
    private Integer numAdults;

    @Column(name = "num_children")
    private Integer numChildren;

    @Column
    private String note;

    @Column
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "bookingDetail")
    private List<HostedAt> hostedAts;

    @JsonIgnore
    @OneToMany(mappedBy = "bookingDetail")
    private List<BookingDetailHistory> bookingDetailHistories;

}
