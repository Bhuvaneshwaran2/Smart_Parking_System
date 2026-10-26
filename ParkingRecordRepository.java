package com.smartparking.repo;
import com.smartparking.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord,Long> {}
