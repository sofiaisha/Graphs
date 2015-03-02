package com.mygraph.core;

import java.util.HashMap;

public class VertexList<T extends Vertex> {
	
	private int 				sourceId;
	private HashMap<T, Integer> edges;
	
	VertexList(int sourceId) {
		this.sourceId = sourceId;
		this.edges = new HashMap<T, Integer>();
	}
	
	int countEdges() {
		return edges.size();
	}
	
	boolean addEdge(T destination, int weight) {
		if (destination == null) {
			return false;
		}
		if (edges.get(destination) != null) {
			return false;
		}
		edges.put(destination, weight);
		return true;		
	}
		
	boolean removeEdge(T destination) {
		if (destination == null) {
			return false;
		}
		return (edges.remove(destination) != null);
	}
	
	boolean hasEdgeTo(T destination) {
		if (destination == null) {
			return false;
		}
		return (edges.get(destination) != null);
	}
	
	boolean hasEdgeTo(T destination, int weight) {
		if (destination == null) {
			return false;
		}
		Integer i = edges.get(destination);
		if ((i != null) && (i == weight)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	int isWeight(T destination) {
		if (destination == null) {
			return 0;
		}
		Integer i = edges.get(destination);
		if (i != null) {
			return i;
		}
		else {
			return 0;
		}
	}
	
	public String toString() {
		return "("+sourceId+")->"+edges;
	}
}

