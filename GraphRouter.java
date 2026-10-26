package com.smartparking.service;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class GraphRouter {
 private final Map<String,Map<String,Integer>> graph=new LinkedHashMap<>();
 public GraphRouter(){
  edge("ENTRY","J1",4); edge("ENTRY","J2",6); edge("J1","J2",2); edge("J1","A",3);
  edge("J2","B",3); edge("J2","C",4); edge("A","B",2); edge("A","D",5);
  edge("B","C",1); edge("B","D",3); edge("C","E",2); edge("D","E",2);
  edge("E","F",3); edge("D","F",5);
 }
 private void edge(String a,String b,int w){graph.computeIfAbsent(a,k->new LinkedHashMap<>()).put(b,w);graph.computeIfAbsent(b,k->new LinkedHashMap<>()).put(a,w);}
 public Map<String,Object> shortestPath(String start,String end){
  if(!graph.containsKey(start)||!graph.containsKey(end)) throw new IllegalArgumentException("Unknown navigation node.");
  Map<String,Integer> dist=new HashMap<>();Map<String,String> prev=new HashMap<>();
  graph.keySet().forEach(n->dist.put(n,Integer.MAX_VALUE));dist.put(start,0);
  PriorityQueue<String> pq=new PriorityQueue<>(Comparator.comparingInt(dist::get));pq.add(start);
  Set<String> done=new HashSet<>();
  while(!pq.isEmpty()){
   String u=pq.poll();if(!done.add(u))continue;if(u.equals(end))break;
   for(var e:graph.get(u).entrySet()){int nd=dist.get(u)+e.getValue();if(nd<dist.get(e.getKey())){dist.put(e.getKey(),nd);prev.put(e.getKey(),u);pq.add(e.getKey());}}
  }
  if(dist.get(end)==Integer.MAX_VALUE) throw new IllegalArgumentException("No route found.");
  List<String> path=new ArrayList<>();for(String at=end;at!=null;at=prev.get(at))path.add(at);Collections.reverse(path);
  return Map.of("start",start,"destination",end,"path",path,"distance",dist.get(end),"algorithm","Dijkstra");
 }
 public Map<String,Object> graph(){return Map.of("nodes",graph.keySet(),"edges",graph.entrySet().stream().flatMap(e->e.getValue().entrySet().stream().filter(x->e.getKey().compareTo(x.getKey())<0).map(x->Map.of("from",e.getKey(),"to",x.getKey(),"weight",x.getValue()))).toList());}
 public List<String> nodes(){return new ArrayList<>(graph.keySet());}
}
