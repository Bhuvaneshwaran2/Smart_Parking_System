# Smart Parking Allocation and Navigation Optimization System

A browser-based DAA project built with Java 17, Spring Boot, Spring Data JPA, MySQL, HTML, CSS and JavaScript.

## Features
- Dashboard with live capacity and occupancy statistics
- Parking slot allocation based on availability, accessibility requirement and shortest reachable route
- Release occupied spaces by clicking their cards
- Route planner using Dijkstra's algorithm on a weighted, undirected graph
- Persistent allocation history in MySQL
- Algorithm reference panel: Binary Search, Merge Sort, BFS, DFS, Dijkstra, Prim, Kruskal, Bellman-Ford, Floyd-Warshall, 0/1 Knapsack, Backtracking, Branch and Bound, approximation concepts
- Seed data is inserted automatically when the parking table is empty

## Requirements
- JDK 17 or later
- Maven 3.9+
- MySQL 8.x

## Run locally
1. Start MySQL.
2. Create the database (optional; `createDatabaseIfNotExist=true` is enabled):
   ```sql
   CREATE DATABASE smart_parking;
   ```
3. Configure credentials. Defaults are `root` / `root`; change them for your local machine:
   - Windows PowerShell:
     ```powershell
     $env:DB_USER="root"
     $env:DB_PASSWORD="your_mysql_password"
     ```
   - macOS/Linux:
     ```bash
     export DB_USER=root
     export DB_PASSWORD='your_mysql_password'
     ```
   You can also edit `src/main/resources/application.properties`.
4. From this folder, run:
   ```bash
   mvn spring-boot:run
   ```
5. Open http://localhost:8080

## Build executable JAR
```bash
mvn clean package
java -jar target/smart-parking-system-1.0.0.jar
```


## Run on GitHub Codespaces (Java + MySQL)

This repository includes `.devcontainer/` configuration. After uploading all files to GitHub:
1. Open the repository on GitHub.
2. Click **Code** → **Codespaces** → **Create codespace on main**.
3. Wait for the environment to finish building. The configured MySQL 8 service starts with the Codespace.
4. In the Codespaces terminal, run `mvn spring-boot:run`.
5. Open the forwarded port **8080** from the **Ports** tab. The app is available through the Codespaces forwarded URL while the Codespace and app are running.

The bundled database credentials are for a demonstration Codespace only. Do not use them for production or expose the MySQL port publicly. The web app port can be made public from the Ports tab if you want to demo it to others; anyone with a public forwarded URL may be able to access it. Stop the Codespace when finished.

## API quick reference
- `GET /api/health`
- `GET /api/stats`
- `GET /api/slots`
- `GET /api/records`
- `GET /api/graph`
- `GET /api/algorithms`
- `GET /api/route?from=ENTRY&to=A`
- `POST /api/allocate` JSON: `{"vehicleNumber":"TN38AB1234","entryNode":"ENTRY","accessible":false}`
- `POST /api/release/P-01`

## Graph model
The sample road network is illustrative, not a real-world map. Nodes: ENTRY, J1, J2, A, B, C, D, E, F. Edge weights represent relative distance units. Dijkstra is appropriate because all edge weights are non-negative.

## Notes on algorithm coverage
This is an integrated working prototype, not a full implementation of every algorithm in all five DAA units. The allocation and route-planning workflow runs Dijkstra in the backend. The algorithm panel summarizes additional DAA topics; these can be expanded into executable demo modules as a follow-up. Sample route weights and seeded records are demonstration data, not measured real-world results.
