package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.Storage;
import com.sw.AurudaTrip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRespository extends JpaRepository<Storage, Long> {
    List<Storage> findAllByUser(User user);
}
