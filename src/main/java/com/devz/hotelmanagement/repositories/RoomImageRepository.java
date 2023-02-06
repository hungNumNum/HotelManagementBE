package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImageRepository extends JpaRepository<Integer, RoomImage> {

}
