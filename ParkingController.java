package com.smartparking.controller;
import com.smartparking.model.ParkingSlot;
import com.smartparking.service.ParkingService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ParkingController {
 private final ParkingService service;
 public ParkingController(ParkingService s){service=s;}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("status","ok","application","Smart Parking Allocation and Navigation Optimization System");}
 @GetMapping("/slots") public Object slots(){return service.allSlots();}
 @GetMapping("/stats") public Object stats(){return service.stats();}
 @GetMapping("/records") public Object records(){return service.allRecords();}
 @GetMapping("/graph") public Object graph(){return service.graph();}
 @GetMapping("/algorithms") public Object algorithms(){return service.algorithms();}
 @GetMapping("/route") public Object route(@RequestParam(defaultValue="ENTRY") String from,@RequestParam String to){return service.route(from,to);}
 @PostMapping("/allocate") public ResponseEntity<?> allocate(@RequestBody AllocationRequest r){
  try{return ResponseEntity.ok(service.allocate(r.vehicleNumber(),r.entryNode(),r.accessible()));}
  catch(IllegalArgumentException|IllegalStateException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 }
 @PostMapping("/release/{code}") public ResponseEntity<?> release(@PathVariable String code){
  try{return ResponseEntity.ok(service.release(code));}
  catch(NoSuchElementException|IllegalStateException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 }
 public record AllocationRequest(String vehicleNumber,String entryNode,boolean accessible){}
}
