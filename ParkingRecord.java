package com.smartparking.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="parking_records")
public class ParkingRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String vehicleNumber;
 private String slotCode;
 private String entryNode;
 private String route;
 private double distance;
 private LocalDateTime allocatedAt;
 public ParkingRecord() {}
 public ParkingRecord(String vehicleNumber,String slotCode,String entryNode,String route,double distance) {
  this.vehicleNumber=vehicleNumber;this.slotCode=slotCode;this.entryNode=entryNode;this.route=route;this.distance=distance;this.allocatedAt=LocalDateTime.now();
 }
 public Long getId(){return id;} public String getVehicleNumber(){return vehicleNumber;} public String getSlotCode(){return slotCode;}
 public String getEntryNode(){return entryNode;} public String getRoute(){return route;} public double getDistance(){return distance;}
 public LocalDateTime getAllocatedAt(){return allocatedAt;}
}
