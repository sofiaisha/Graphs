package com.mygraph.algos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import com.mygraph.core.Graph;
import com.mygraph.core.Vertex;

//As per "Algorithms in a Nutshell", O'Reilly, 2009

class QueueElement<T extends Vertex> {
	private final T vertex;
	private int distance;

	QueueElement(T vertex, int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}

	T getVertex() {
		return vertex;
	}

	int getDistance() {
		return distance;
	}

	void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "(" + vertex.getId() + "," + distance + ")";
	}

}

class QueueElementMinFirst<T extends Vertex> implements
		Comparator<QueueElement<T>> {
	@Override
	public int compare(QueueElement<T> e1, QueueElement<T> e2) {
		int i1 = e1.getDistance();
		int i2 = e2.getDistance();
		if (i1 == i2) {
			return 0;
		}
		if (i1 > i2) {
			return 1;
		} else {
			return -1;
		}
	}
}

// This is the well-known Dijsktra algorithm to find the shortest path from a
// single origin
// This implementation uses a PriorityQueue.
public class DijkstraWithPQ<T extends Vertex> {

	private final Graph<T> g;
	private final int size;
	private final int[] distance;
	private final int[] predecessor;

	DijkstraWithPQ(Graph<T> g) {
		this.g = g;
		size = g.countVertices();
		distance = new int[size + 1];
		predecessor = new int[size + 1];
	}

	public void shortestPath(T start) {
		// Start with infinite distances between the start vertex and all others
		for (int i = 1; i < (this.size + 1); i++) {
			distance[i] = Integer.MAX_VALUE;
			predecessor[i] = -1;
		}
		distance[start.getId()] = 0;

		// Build a list of (vertex, distance) couples
		PriorityQueue<QueueElement<T>> pq = new PriorityQueue<QueueElement<T>>(
				new QueueElementMinFirst<T>());
		for (T v : g.getVertices()) {
			pq.add(new QueueElement<T>(v, distance[v.getId()]));
		}

		while (pq.size() != 0) {

			// System.out.println(pq);

			// Get and remove the vertex that is the closest to the start node
			// (aka the min node)
			// It's the first element of the list
			T min = pq.poll().getVertex();
			assert(min!= null);

			// For each neighbor
			for (T neighbor : g.getNeighbors(min)) {
				// Get its distance to the min node
				int weight = g.isWeight(min, neighbor);
				// Compute its distance from the start node
				int newDistance = distance[min.getId()] + weight;

				// If the distance is smaller than the one currently stored,
				if (newDistance < distance[neighbor.getId()]) {
					// Find the neighbor in the queue
					Iterator<QueueElement<T>> i = pq.iterator();
					while (i.hasNext()) {
						QueueElement<T> e = i.next();
						if (e.getVertex().equals(neighbor)) {
							// Update its distance to the start vertex
							e.setDistance(newDistance);
							// Remove and add again to maintain queue order
							pq.remove(e);
							pq.add(e);
							break;
						}
						// Remember the new distance and the predecessor
						distance[neighbor.getId()] = newDistance;
						predecessor[neighbor.getId()] = min.getId();
					}
				}
			}
		}
	}

	public ArrayList<Integer> findPath(T destination) {
		if (destination == null) {
			return null;
		}
		int currentId = destination.getId();
		// If the destination has no predecessor, there is no path to it
		if (predecessor[currentId] == -1) {
			return null;
		}
		// Walk through the predecessors and add them at the front of the list
		ArrayList<Integer> path = new ArrayList<Integer>();
		while (currentId != -1) {
			path.add(0, currentId);
			currentId = predecessor[currentId];
		}
		// The list contains the path from the start vertex to the destination
		// vertex
		return path;
	}

	public int findPathWeight(T destination) {
		if (destination == null) {
			return 0;
		}
		int currentId = destination.getId();
		// If the destination has no predecessor, there is no path to it
		if (predecessor[currentId] == -1) {
			return 0;
		}
		// Walk through the predecessors and add the weight of each edge
		int weight = 0;
		while (currentId != -1) {
			weight += g.isWeight(g.getVertex(predecessor[currentId]),
					g.getVertex(currentId));
			currentId = predecessor[currentId];
		}
		return weight;
	}
}
