package com.smartparking.repo;
import com.smartparking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot,Long> {
 Optional<ParkingSlot> findByCode(String code);
}
