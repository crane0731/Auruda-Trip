package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.Storage;
import com.sw.AurudaTrip.domain.StorageTravelPlace;
import com.sw.AurudaTrip.domain.StorageTravelPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageTravelPlaceRepository extends JpaRepository<StorageTravelPlace,Long> {

    StorageTravelPlace findById(StorageTravelPlaceId storageTravelPlaceId);
}
