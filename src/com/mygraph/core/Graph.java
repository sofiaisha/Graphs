package com.mygraph.core;

import java.util.HashMap;
import java.util.Set;

public class Graph<T extends Vertex> {

	private final HashMap<T, VertexList<T>> vertices;
	private final boolean isDirected;

	public Graph(boolean isDirected) {
		vertices = new HashMap<T, VertexList<T>>();
		this.isDirected = isDirected;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public int countVertices() {
		return vertices.size();
	}

	public int countEdges() {
		int count = 0;
		for (VertexList<T> v : vertices.values()) {
			count += v.countEdges();
		}
		if (!isDirected()) {
			count /= 2;
		}
		return count;
	}

	public T getVertex(int id) {
		for (T v : vertices.keySet()) {
			if (v.getId() == id) {
				return v;
			}
		}
		return null;
	}

	public Set<T> getNeighbors(T v) {
		if (v == null) {
			return null;
		}
		return vertices.get(v).getNeighbors();
	}

	public Set<T> getVertices() {
		return vertices.keySet();
	}

	// This method adds an edge to the graph
	// It will add the source and destination vertices if needed
	// if the edge already exists, it won't be duplicated
	public boolean addEdge(T source, T destination, int weight) {
		if ((source == null) || (destination == null) || (weight == 0)) {
			return false;
		}
		if (source.getId() == destination.getId()) {
			return false;
		}
		
		// Add vertices if they're not already in the graph
		if (!vertices.containsKey(source)) {
			vertices.put(source, new VertexList<T>(source.getId()));
		}
		if (!vertices.containsKey(destination)) {
			vertices.put(destination, new VertexList<T>(destination.getId()));
		}
		// Add edge
		boolean src;
		boolean dst = true;
		src = vertices.get(source).addEdge(destination, weight);
		// If the graph is not directed, we need to add the reciprocal edge to
		// the destination
		if (!isDirected()) {
			dst = vertices.get(destination).addEdge(source, weight);
		}
		return (src && dst);
	}

	public boolean addEdge(T source, T destination) {
		return addEdge(source, destination, 1);
	}

	public boolean removeEdge(T source, T destination) {
		if ((source == null) || (destination == null)) {
			return false;
		}
		boolean src;
		boolean dst = true;
		src = vertices.get(source).removeEdge(destination);
		if (!isDirected()) {
			dst = vertices.get(destination).removeEdge(source);
		}
		return (src && dst);
	}

	public boolean hasEdge(T source, T destination, int weight) {
		if ((source == null) || (destination == null) || (weight == 0)) {
			return false;
		}
		return vertices.get(source).hasEdgeTo(destination, weight);
	}

	public boolean hasEdge(T source, T destination) {
		if ((source == null) || (destination == null)) {
			return false;
		}
		return vertices.get(source).hasEdgeTo(destination);
	}

	public int getWeight(T source, T destination) {
		if ((source == null) || (destination == null)) {
			return 0;
		}
		return vertices.get(source).getWeight(destination);
	}

	public void display() {
		System.out.println("Vertices: " + countVertices() + ", Edges: "
				+ countEdges());
		for (VertexList<T> v : vertices.values()) {
			System.out.println(v);
		}
	}
}
