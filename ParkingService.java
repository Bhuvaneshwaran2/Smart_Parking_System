package com.smartparking.service;
import com.smartparking.model.*;
import com.smartparking.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ParkingService {
 private final ParkingSlotRepository slots; private final ParkingRecordRepository records; private final GraphRouter router;
 public ParkingService(ParkingSlotRepository s,ParkingRecordRepository r,GraphRouter g){slots=s;records=r;router=g;}
 public List<ParkingSlot> allSlots(){return slots.findAll().stream().sorted(Comparator.comparing(ParkingSlot::getCode)).toList();}
 public List<ParkingRecord> allRecords(){return records.findAll().stream().sorted(Comparator.comparing(ParkingRecord::getAllocatedAt).reversed()).toList();}
 public Map<String,Object> stats(){List<ParkingSlot> all=slots.findAll();long occupied=all.stream().filter(ParkingSlot::isOccupied).count();return Map.of("total",all.size(),"occupied",occupied,"available",all.size()-occupied,"occupancyPercent",all.isEmpty()?0:Math.round(occupied*100.0/all.size()));}
 @Transactional public Map<String,Object> allocate(String vehicle,String entry,boolean accessible){
  if(vehicle==null||vehicle.isBlank())throw new IllegalArgumentException("Vehicle number is required.");
  if(entry==null||entry.isBlank())entry="ENTRY";
  final String origin=entry;
  List<ParkingSlot> candidates=slots.findAll().stream().filter(s->!s.isOccupied()&&(!accessible||s.isAccessible())).toList();
  if(candidates.isEmpty())throw new IllegalStateException("No suitable parking slots are available.");
  ParkingSlot best=null;Map<String,Object> bestRoute=null;
  for(ParkingSlot s:candidates){try{Map<String,Object> route=router.shortestPath(origin,s.getNode());if(bestRoute==null||((Number)route.get("distance")).intValue()<((Number)bestRoute.get("distance")).intValue()){best=s;bestRoute=route;}}catch(IllegalArgumentException ignored){}}
  if(best==null)throw new IllegalArgumentException("No route from "+origin+" to an available slot.");
  best.setOccupied(true);best.setVehicleNumber(vehicle.toUpperCase(Locale.ROOT));slots.save(best);
  ParkingRecord rec=records.save(new ParkingRecord(vehicle.toUpperCase(Locale.ROOT),best.getCode(),origin,String.join(" → ",(List<String>)bestRoute.get("path")),((Number)bestRoute.get("distance")).doubleValue()));
  return Map.of("message","Parking allocated successfully","slot",best,"route",bestRoute,"recordId",rec.getId());
 }
 @Transactional public ParkingSlot release(String code){
  ParkingSlot s=slots.findByCode(code).orElseThrow(()->new NoSuchElementException("Parking slot not found: "+code));
  if(!s.isOccupied())throw new IllegalStateException("This slot is already available.");
  s.setOccupied(false);s.setVehicleNumber(null);return slots.save(s);
 }
 public Map<String,Object> route(String start,String end){return router.shortestPath(start,end);}
 public Map<String,Object> graph(){return router.graph();}
 public Map<String,Object> algorithms(){return Map.of(
  "Dijkstra","Shortest route in a non-negative weighted graph: O((V+E) log V) with a priority queue.",
  "BFS","Visits nodes level-by-level: O(V+E); useful for unweighted shortest paths.",
  "DFS","Depth-first graph traversal: O(V+E); useful for connectivity exploration.",
  "Merge Sort","Stable sorting: O(n log n) time and O(n) auxiliary space.",
  "Binary Search","Searches a sorted collection: O(log n) time.",
  "0/1 Knapsack","Dynamic programming: O(nW) time and O(W) optimized space.",
  "Kruskal / Prim","Minimum spanning tree algorithms; commonly O(E log E) and O(E log V).",
  "Bellman-Ford / Floyd-Warshall","Alternative shortest-path algorithms: O(VE) and O(V^3).",
  "Backtracking / Branch and Bound","Explore constrained allocation choices; worst-case exponential.",
  "Approximation","Fast near-optimal methods for larger combinatorial allocation instances."
 );}
 public void seedIfEmpty(){
  if(slots.count()>0)return;
  slots.saveAll(List.of(new ParkingSlot("P-01","A","A",true,false),new ParkingSlot("P-02","A","A",false,false),
   new ParkingSlot("P-03","B","B",false,true),new ParkingSlot("P-04","B","C",true,false),
   new ParkingSlot("P-05","C","D",false,false),new ParkingSlot("P-06","C","E",false,false),
   new ParkingSlot("P-07","D","F",true,false),new ParkingSlot("P-08","D","F",false,false)));
 }
}
