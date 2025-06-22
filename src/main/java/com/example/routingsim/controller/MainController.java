package com.example.routingsim.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.routingsim.model.Graph;
import com.example.routingsim.model.LogEntry;
import com.example.routingsim.model.Node;

import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class MainController {

    // ── FXML refs ───────────────────────────────────────────────────────────────
    @FXML private Pane graphPane;
    @FXML private Button addNodeBtn, selectRouteBtn, runBtn,
                         clearPathBtn, removeNodeBtn;
    @FXML private TableView<LogEntry> logTable;
    @FXML private TableColumn<LogEntry,String> timeCol, eventCol, detailCol;
    @FXML private RadioButton hopRadio, weightRadio;

    // ── data model / visual maps ────────────────────────────────────────────────
    private final Graph graph = new Graph();
    private int nodeCounter = 0;

    private final Map<Node, StackPane> nodeVisual  = new HashMap<>();
    private final Map<Set<Node>, Line>  edgeLineMap  = new HashMap<>();
    private final Map<Set<Node>, Label> edgeLabelMap = new HashMap<>();

    private final List<Line>   routeLines  = new ArrayList<>();
    private final List<Circle> packetIcons = new ArrayList<>();

    // route & remove mode
    private Node src, dst;
    private boolean routeMode = false;
    private boolean removeMode = false;
    private Node lastSrc, lastDst;

    // helpers
    private final List<Node> edgeCandidates = new ArrayList<>();

    // ── init ────────────────────────────────────────────────────────────────────
    @FXML private void initialize() {
        setupTable();

        addNodeBtn.setOnAction(e -> {
            graphPane.setCursor(javafx.scene.Cursor.CROSSHAIR);
            log("UI","Info","Click canvas to place node");
        });

        selectRouteBtn.setOnAction(e -> {
            routeMode = true;  removeMode = false;
            src = dst = null;
            log("UI","Info","Click Source then Destination");
        });

        removeNodeBtn.setOnAction(e -> {
            removeMode = true; routeMode = false;
            graphPane.setCursor(javafx.scene.Cursor.CROSSHAIR);
            log("UI","Remove","Click a node to delete it");
        });

        runBtn.setOnAction(e -> rerunLastRoute());
        clearPathBtn.setOnAction(e -> clearRoute());

        graphPane.setOnMouseClicked(e -> {
            if (graphPane.getCursor() == javafx.scene.Cursor.CROSSHAIR &&
                e.getButton() == MouseButton.PRIMARY &&
                !routeMode && !removeMode) {
                createNode(e.getX(), e.getY());
                graphPane.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });

        graphPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) deleteSelection();
        });
        graphPane.setFocusTraversable(true);
    }

    // ── node creation ───────────────────────────────────────────────────────────
    private void createNode(double x, double y) {
        String id = "N" + (++nodeCounter);
        Node n = graph.addNode(id, x, y);

        Circle circle = new Circle(14, Color.web("#4fc3f7"));
        circle.setStroke(Color.WHITE);
        Label  label  = new Label(id);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size:10;");

        StackPane wrapper = new StackPane(circle, label);
        wrapper.setLayoutX(x - 14);
        wrapper.setLayoutY(y - 14);
        wrapper.setOnMouseClicked(ev -> nodeClicked(ev.getButton(), n));
        nodeVisual.put(n, wrapper);
        graphPane.getChildren().add(wrapper);
        log("Graph","Add","Node "+id);
    }

    private Node selectedNode = null;
    private void nodeClicked(MouseButton btn, Node n) {
        selectedNode = n;

        // Remove-node mode
        if (removeMode) {
            deleteWholeNode(n);
            removeMode = false;
            graphPane.setCursor(javafx.scene.Cursor.DEFAULT);
            return;
        }

        // Route-selection mode
        if (routeMode) {
            if (src == null)      { src = n; log("UI","Route","Source = "+n.getId()); }
            else if (dst == null) { dst = n; log("UI","Route","Dest   = "+n.getId());
                                    simulateAODV(src,dst); routeMode=false; }
            return;
        }

        // Edge creation (normal mode)
        edgeCandidates.add(n);
        if (edgeCandidates.size()==2) {
            Node a = edgeCandidates.get(0), b = edgeCandidates.get(1);
            if (!a.equals(b)) promptWeightAndCreateEdge(a,b);
            edgeCandidates.clear();
        }
    }

    // ── edge creation ───────────────────────────────────────────────────────────
    private void promptWeightAndCreateEdge(Node a, Node b) {
        TextInputDialog d = new TextInputDialog("1.0");
        d.setHeaderText("Weight "+a.getId()+" ↔ "+b.getId());
        Optional<String> res = d.showAndWait();
        res.ifPresent(txt -> {
            try {
                double w = Double.parseDouble(txt);
                graph.addEdge(a.getId(), b.getId(), w);
                drawOrUpdateEdge(a,b,w);
                log("Graph","Add/Upd","Edge "+a.getId()+"↔"+b.getId()+" w="+w);
            } catch(NumberFormatException ex){ log("Error","Edge","Bad weight"); }
        });
    }

    private void drawOrUpdateEdge(Node a, Node b, double w){
        Set<Node> key = Set.of(a,b);
        if(edgeLabelMap.containsKey(key)){
            edgeLabelMap.get(key).setText(String.valueOf(w));
            return;
        }
        Line line = new Line(a.getX(),a.getY(),b.getX(),b.getY());
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        line.setOnMouseClicked(ev -> { if(ev.getButton()==MouseButton.PRIMARY) selectEdge(line,a,b); });
        graphPane.getChildren().add(0,line);

        Label lbl = new Label(String.valueOf(w));
        lbl.setTextFill(Color.YELLOW);
        lbl.setStyle("-fx-font-size:10;");
        lbl.setLayoutX((a.getX()+b.getX())/2);
        lbl.setLayoutY((a.getY()+b.getY())/2);
        graphPane.getChildren().add(lbl);

        edgeLineMap.put(key, line);
        edgeLabelMap.put(key, lbl);
    }

    // ── AODV simulation ─────────────────────────────────────────────────────────
    private void simulateAODV(Node source, Node dest){
        clearRoute();
        log("Sim","Start","AODV "+source.getId()+"→"+dest.getId());

        boolean useWeight = weightRadio.isSelected();
        List<Node> path = useWeight
                ? graph.findWeightedShortestPath(source, dest)
                : graph.findShortestPath(source, dest);

        if(path==null){ log("Sim","Fail","No path"); return; }

        lastSrc=source; lastDst=dest;
        animateRoute(path);
    }

    private void animateRoute(List<Node> path){
        for(int i=0;i<path.size()-1;i++){
            Node a=path.get(i), b=path.get(i+1);
            Line l=new Line(a.getX(),a.getY(),b.getX(),b.getY());
            l.setStroke(Color.web("#00e676"));
            l.setStrokeWidth(3);
            l.getStrokeDashArray().addAll(6d,4d);
            graphPane.getChildren().add(l);
            routeLines.add(l);
        }

        Polyline routePoly = new Polyline();
        for(Node n : path) routePoly.getPoints().addAll(n.getX(), n.getY());

        Circle pkt = new Circle(6, Color.RED);
        graphPane.getChildren().add(pkt);
        packetIcons.add(pkt);

        PathTransition pt = new PathTransition(Duration.seconds(1.2*path.size()), routePoly, pkt);
        pt.setOnFinished(e -> log("Sim","Done","Reached "+path.get(path.size()-1).getId()));
        pt.play();

        log("Sim","Path", String.join(" → ", path.stream().map(Node::getId).toList()));
    }

    // ── Clear / rerun helpers ───────────────────────────────────────────────────
    private void clearRoute(){
        routeLines.forEach(graphPane.getChildren()::remove);
        packetIcons.forEach(graphPane.getChildren()::remove);
        routeLines.clear(); packetIcons.clear();
    }
    private void rerunLastRoute(){
        if(lastSrc!=null && lastDst!=null) simulateAODV(lastSrc,lastDst);
        else log("UI","Info","No previous src/dst. Use Select Route first.");
    }

    // ── deletion helpers ───────────────────────────────────────────────────────
    private Line selectedEdge=null; private Node edgeA,edgeB;
    private void selectEdge(Line l, Node a, Node b){ selectedEdge=l; edgeA=a; edgeB=b; }

    private void deleteSelection(){
        if(selectedEdge!=null){
            Set<Node> key = Set.of(edgeA,edgeB);
            graphPane.getChildren().removeAll(selectedEdge, edgeLabelMap.remove(key));
            edgeLineMap.remove(key);
            graph.removeEdge(edgeA,edgeB);
            log("Graph","Del","Edge "+edgeA.getId()+"↔"+edgeB.getId());
            selectedEdge=null; return;
        }
        if(selectedNode!=null) deleteWholeNode(selectedNode);
    }

    private void deleteWholeNode(Node n){
        // remove node visual
        graphPane.getChildren().remove(nodeVisual.remove(n));

        // remove incident edges (lines + labels)
        edgeLineMap.keySet().stream()
                .filter(pair -> pair.contains(n))
                .toList()
                .forEach(pair -> {
                    graphPane.getChildren().remove(edgeLineMap.remove(pair));
                    graphPane.getChildren().remove(edgeLabelMap.remove(pair));
                });

        // remove from model
        graph.removeNode(n);
        log("Graph","Del","Node "+n.getId()+" and its edges removed");
        selectedNode = null;
    }

    // ── log helper ─────────────────────────────────────────────────────────────
    private final ObservableList<LogEntry> logs = FXCollections.observableArrayList();
    private void setupTable(){
        timeCol.setCellValueFactory(d -> d.getValue().time);
        eventCol.setCellValueFactory(d -> d.getValue().event);
        detailCol.setCellValueFactory(d -> d.getValue().detail);
        logTable.setItems(logs);
    }
    private void log(String event,String detail){ log("App",event,detail); }
    private void log(String who,String event,String detail){
        logs.add(new LogEntry(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                              who+":"+event, detail));
        logTable.scrollTo(logs.size()-1);
    }
}
