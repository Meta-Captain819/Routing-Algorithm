## 📌 Project Overview

This project is a **Graphical Simulation of the AODV (Ad hoc On-Demand Distance Vector) Routing Protocol**, built using **JavaFX**. It allows users to create, visualize, and simulate dynamic network topologies interactively, enabling a deep understanding of how routing protocols work in real-world ad hoc networks.

The system provides a visually engaging way to:

- ✅ Create and remove nodes dynamically on a canvas  
- 🔗 Add and modify weighted edges between nodes  
- 🎯 Select source and destination nodes for route discovery  
- 🚀 Simulate the **AODV routing algorithm** based on hop count or edge weights  
- 📈 View animated packet routing with detailed logs and timestamps  

Designed as an educational tool, this simulator aims to assist students, researchers, and instructors in comprehending the internal mechanics of routing protocols through hands-on experimentation.

## 📑 Table of Contents

1. [Project Description](#project-overview)
2. [Objectives](#objectives)
3. [Project Directory Structure](#project-directory-structure)
4. [Key Features](#key-features)
5. [Technologies Used](#technologies-used)
6. [Installation & Setup](#installation-and-setup)
7. [Application Walkthrough](#application-walkthrough)
8. [Algorithms Implemented](#algorithms-implemented)
9. [Simulation Metrics](#simulation-metrics)
10. [Testing & Validation](#testing-and-validation)
11. [Learning Outcomes](#learning-outcomes)
12. [License](#license)


##  Objectives

The main goals of this project are to:

- 🧠 **Understand AODV Routing Protocol:** Provide a practical visualization of how the AODV (Ad hoc On-Demand Distance Vector) protocol works in mobile ad hoc networks.
  
- 🛠️ **Enable Interactive Learning:** Allow users to manually build network topologies and simulate dynamic routing decisions in real-time.

- ⚙️ **Implement Routing Metrics:** Support both **hop-count-based** and **weight-based** routing simulations for flexible analysis.

- 🔄 **Support Dynamic Network Changes:** Allow users to add or remove nodes and connections to simulate real-world network conditions such as node failure or mobility.

- 📊 **Provide Simulation Feedback:** Offer detailed log entries with timestamps and event breakdowns to enhance user understanding of how routes are discovered and packets are delivered.

- 🧪 **Serve as a Learning and Testing Tool:** Assist students, educators, and researchers in exploring and testing routing logic in a safe, controlled, and visual environment.

> This tool is designed not just as a simulation, but as a bridge between theory and practical understanding of ad hoc routing systems.

##  Project Directory Structure

```
/
├── .gradle/
├── bin/
│ └── main/
├── build/
├── gradle/
├── src/
│ └── main/
│ ├── java/
│ │ └── com/
│ │ └── example/
│ │ └── routingsim/
│ │ ├── controller/
│ │ │ └── MainController.java
│ │ ├── model/
│ │ │ ├── Edge.java
│ │ │ ├── Graph.java
│ │ │ ├── LogEntry.java
│ │ │ └── Node.java
│ │ └── view/
│ │ └── MainApp.java
│ └── resources/
│ └── fxml/
│ └── MainView.fxml
├── test/
│ └── java/
├── .gitattributes
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

This structure clearly reflects the **MVC pattern** and keeps logic, UI, and configurations neatly organized for easier maintenance and scalibility.

##  Key Features

- 🖱️ **Interactive Graph Construction**
  - Click to add nodes anywhere on the canvas.
  - Select two nodes to create weighted edges between them.

- 📍 **Node and Edge Management**
  - Add or remove nodes dynamically.
  - Automatically remove all connected edges when a node is deleted.
  - Edge weights can be updated by clicking the same pair of nodes again.

- 🔀 **Routing Metric Selection**
  - Toggle between **Hop Count (AODV)** and **Weighted Path** for route discovery.
  - Use radio buttons to select the desired routing strategy.

- 🧭 **AODV Route Simulation**
  - Simulate route discovery using the AODV protocol.
  - Visualize route discovery and packet travel with animated paths.

- 📦 **Packet Animation**
  - Real-time animation shows packet moving from source to destination.
  - Color-coded paths help differentiate the final selected route.

- 🧹 **Clear & Rerun**
  - Easily clear the current path.
  - Re-run the last selected route without reselecting nodes.

- 🪪 **Node Naming**
  - Each node is automatically labeled (Node 1, Node 2, ...).

- 📋 **Structured Log System**
  - Simulation logs are displayed in a table with timestamp, event type, and details.
  - Helps in analyzing each step of the route discovery and transmission process.

- 🧪 **Dynamic Interaction Modes**
  - Switch between modes: Add Node, Select Route, Run AODV, Remove Node — each with visual cues and logs.

- 🎨 **Modern JavaFX Interface**
  - Clean UI with color-coded feedback and intuitive controls.
  - Designed for clarity and engagement, especially for educational purposes.

##  Technologies Used

This project was built using the following tools and technologies:

- **Java 21** – Core programming language used for application logic.
- **JavaFX** – For building the graphical user interface (GUI).
- **FXML** – XML-based layout for designing JavaFX UI structure.
- **Gradle** – Build automation tool used to manage dependencies and build lifecycle.
- **Scene Builder** – Visual UI editor for designing JavaFX components.
- **IntelliJ IDEA / VS Code** – Recommended IDEs for development and debugging.
- **JavaFX PathTransition** – For animating packet movement across the network graph.
- **Git & GitHub** – Version control and collaboration.

> ⚙️ The application leverages JavaFX’s capabilities to deliver an interactive and animated simulation environment for AODV and weighted path routing.

##  Installation and Setup

Follow these steps to set up and run the project on your local machine:

### 1. Clone the Repository

```bash
git clone https://github.com/Meta-Captain819/Routing-Algorithm.git
cd routing-sim
```

### 2. Install Java JDK (Version 21)
Ensure Java is installed and properly configured in your system.

### 3. Set Environment Variables
Set JAVA_HOME in Windows
- Open Environment Variables
- Add a new `JAVA_HOME` variable
- Set  variable path accordingly for e.g `C:\Program Files\Java\jdk-21`
- Now click on Path on System Variable and click new and paste this `%JAVA_HOME%\bin`

### 4. Install JavaFX
Configure JavaFX in your IDE or add module arguments when running.

### Build & Run the project

Open Terminal and run this command

```bash
./gradlew build
./gradlew clean run
```
##  Application Walkthrough

This walkthrough provides a step-by-step overview of how the application works and what each part does visually and functionally.

### 🖼️ Interface Overview

The application window is divided into two main areas:
- **Canvas (Center Pane):** Where nodes and edges are visually placed and connected.
- **Control Panel (Right Side):** Contains all interactive buttons and options for the user.

---

### 🟨 Step-by-Step Flow

#### 1. **Start with Adding Nodes**
- Click `Add Node`, then click on the canvas.
- A circular node appears with a label (e.g., N1).
- Repeat to add more nodes.

#### 2. **Connect Nodes via Edges**
- Click on two nodes one after the other.
- A prompt asks for the **weight** of the edge (e.g., `5.0`).
- An edge is drawn and the weight is shown in yellow between the nodes.

#### 3. **Choose Routing Metric**
- Choose either:
  - `Hop Count (AODV)` for fewer hops.
  - `Weighted Path` to consider edge weights.
- This affects the simulation route.

#### 4. **Select Source and Destination**
- Click `Select Route`, then click two nodes:
  - First: Source Node
  - Second: Destination Node

#### 5. **Run the Simulation**
- Click `Run AODV`.
- A red circle (packet) animates from the source to the destination.
- The shortest path is drawn in **green dashed lines**.
- The Simulation Log Table records every step (time, event, details).

#### 6. **Clear the Path**
- Click `Clear Path` to remove the current route and packet animation.

#### 7. **Remove Node**
- Click `Remove Node`, then select a node on the canvas.
- The selected node and all its connected edges and weights are removed.

---

### 🧾 Simulation Log Table
- Located at the bottom of the control panel.
- Shows:
  - **Time** of event
  - **Type** of event (e.g., Add, Delete, Route)
  - **Details** (e.g., source, destination, weight)

This helps track all actions and understand how the routing simulation progressed.

##  Algorithms Implemented

This simulation application supports routing algorithms tailored for network pathfinding, especially in dynamic and weighted topologies.

### 1. **AODV (Ad-hoc On-Demand Distance Vector) Routing**

AODV is a reactive routing protocol used in wireless ad hoc networks. The application simulates its simplified behavior with the following key features:

- **Route Discovery on Demand:**
  - Routes are discovered only when needed (i.e., when you click "Select Route").
  - Simulates RREQ (Route Request) and RREP (Route Reply) messages.

- **Hop Count Metric:**
  - Uses the number of hops (edges) between source and destination as the primary metric for routing.

- **No Routing Tables:**
  - In this simulation, route states are not cached. The shortest path is recalculated each time.

---

### 2. **Weighted Shortest Path (Dijkstra's Algorithm)**

When the user selects the **"Weighted Path"** option:

- **Dijkstra’s Algorithm** is used to find the path with the **minimum total edge weight**.
- Edge weights are manually entered by the user during edge creation.
- The path calculation considers the cost rather than just the number of hops.

---

### Routing Metric Toggle

The application provides a **toggle switch** between:
- **Hop Count** (Unweighted shortest path – classic AODV style)
- **Weighted Path** (Dijkstra's Algorithm – used in real-world weighted networks)

---

### Visualization
- Paths are visualized in real-time using animated red "packet" movements.
- Route discovery and traversal are logged step-by-step in the simulation log.


##  Simulation Metrics

The simulation provides real-time metrics and visual feedback to help users analyze and understand the behavior of the routing algorithms. The following metrics and data are displayed or logged during the simulation:

### 1. **Routing Metric Selection**
- **Hop Count**: Measures the number of nodes (hops) between source and destination.
- **Weighted Path**: Calculates the total weight of the edges along the path.

> Users can toggle between these metrics using the radio buttons provided in the UI.

---

### 2. **Simulation Log**
A structured table displays:
- 🕒 **Time**: Timestamp of the event.
- 🧩 **Event Type**: Such as Node Added, Route Selected, or Simulation Started.
- 📋 **Details**: Specific details related to each event (e.g., path discovered, weight values, failure messages).

---

### 3. **Visual Indicators**
- **Route Lines**: Displayed in green dashes to highlight the final chosen route.
- **Packet Animation**: A red dot travels across the path to simulate packet transmission.
- **Edge Weights**: Clearly labeled on the graph to assist in visual path comparison.

---

### 4. **Node and Edge Tracking**
- Every node and edge creation, update, or removal is logged and visually represented.
- Edge weights can be edited, and changes are reflected both visually and in path calculations.

---

### 5. **Performance Feedback**
- Pathfinding response is near-instantaneous.
- Logs indicate success or failure in route discovery.

These metrics provide an educational and interactive experience, especially useful for learning about network routing protocols and graph algorithms.


##  Testing and Validation

Robust testing and validation were carried out to ensure the accuracy, stability, and usability of the routing simulation tool. Various aspects of the application were examined through manual and functional testing.

---

### 1. **Functionality Testing**
Each core feature was tested individually and in combination:
- ✅ Node addition, labeling, and placement across the canvas.
- ✅ Edge creation with accurate weight assignment and display.
- ✅ Simulation of shortest path using both hop count and weighted metrics.
- ✅ Toggle between different routing algorithms without breaking functionality.
- ✅ Node and edge deletion with proper visual and data model updates.

---

### 2. **Pathfinding Accuracy**
- Simulated various network topologies to verify that:
  - The AODV algorithm correctly finds the shortest path based on the selected metric.
  - The weighted path simulation considers real-time updated weights.
- Compared output paths against manually calculated expected results.

---

### 3. **UI Responsiveness**
- Verified seamless user interaction for:
  - Button clicks and their corresponding actions.
  - Dynamic updates in the graph pane (like animation and routing lines).
  - Real-time logging in the simulation table.

---

### 4. **Edge Case Handling**
Tested scenarios included:
- Attempting to create duplicate nodes or edges.
- Selecting the same node as both source and destination.
- Running simulations without selecting a route.
- Removing nodes involved in active routes.

---

### 5. **Error Feedback and Logging**
- Implemented structured and time-stamped logs to capture every interaction and simulation event.
- Errors (e.g., invalid weights, unreachable destinations) are gracefully handled and clearly reported.

---

### 6. **Cross-Platform Compatibility**
- Confirmed smooth execution on multiple systems using Java 21 and JavaFX 21.
- Tested with Gradle to ensure reliable building and launching on both Windows and Linux environments.

> **Outcome**: All features passed the test scenarios successfully, confirming the system’s correctness and user-friendliness.


##  Learning Outcomes

This project provided hands-on experience with both theoretical and practical aspects of computer networks, Java development, and simulation-based learning. Key learning outcomes include:

---

### 1. **Understanding of Routing Algorithms**
- Gained in-depth knowledge of the **AODV (Ad hoc On-Demand Distance Vector)** protocol and its real-world applications.
- Explored and implemented **shortest path algorithms** based on both **hop count** and **weighted metrics**.
- Learned how routing decisions adapt to network topology changes and edge weights.

---

### 2. **Java and JavaFX Mastery**
- Acquired proficiency in building rich desktop applications using **JavaFX**.
- Used **FXML** for declarative UI design and **MVC pattern** to separate concerns cleanly.
- Mastered the use of **Java collections**, event handling, and animation APIs.

---

### 3. **Graph Theory in Practice**
- Translated abstract graph concepts (nodes, edges, adjacency lists) into concrete, interactive simulations.
- Understood how graph traversal and pathfinding can be visualized and animated.

---

### 4. **UI/UX Design and Event-Driven Programming**
- Designed a **user-friendly interface** allowing dynamic graph construction and interaction.
- Developed skills in managing **mouse events**, **radio buttons**, and **interactive visual components**.

---

### 5. **Software Design and Debugging**
- Followed a **modular development approach** for maintainability.
- Practiced **debugging techniques**, especially related to JavaFX rendering and Gradle project structure.
- Understood how to handle **runtime exceptions**, FXML loading issues, and invalid input cases.

---

### 6. **Version Control and Documentation**
- Applied **Git/GitHub** for source code versioning and collaboration.
- Learned how to create professional-grade documentation including this detailed **README.md**.

> **Summary**: The project helped strengthen core software engineering skills and provided practical exposure to networking concepts through simulation.


##  License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

You are free to:

- ✅ Use
- ✅ Copy
- ✅ Modify
- ✅ Merge
- ✅ Publish
- ✅ Distribute

As long as you include the original copyright and 
permission notice in all copies or substantial portions of the software.





