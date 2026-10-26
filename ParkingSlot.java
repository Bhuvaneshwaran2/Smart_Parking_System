package com.smartparking.model;
import jakarta.persistence.*;
@Entity
@Table(name="parking_slots")
public class ParkingSlot {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, unique=true) private String code;
 @Column(nullable=false) private String zone;
 @Column(nullable=false) private String node;
 @Column(nullable=false) private boolean accessible;
 @Column(nullable=false) private boolean occupied;
 private String vehicleNumber;
 public ParkingSlot() {}
 public ParkingSlot(String code,String zone,String node,boolean accessible,boolean occupied) {
  this.code=code; this.zone=zone; this.node=node; this.accessible=accessible; this.occupied=occupied;
 }
 public Long getId(){return id;} public String getCode(){return code;} public void setCode(String v){code=v;}
 public String getZone(){return zone;} public void setZone(String v){zone=v;}
 public String getNode(){return node;} public void setNode(String v){node=v;}
 public boolean isAccessible(){return accessible;} public void setAccessible(boolean v){accessible=v;}
 public boolean isOccupied(){return occupied;} public void setOccupied(boolean v){occupied=v;}
 public String getVehicleNumber(){return vehicleNumber;} public void setVehicleNumber(String v){vehicleNumber=v;}
}
