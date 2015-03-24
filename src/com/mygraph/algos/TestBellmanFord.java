package com.mygraph.algos;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mygraph.core.Graph;
import com.mygraph.core.Vertex;

public class TestBellmanFord {

	Vertex v1, v2, v3, v4, v5, v6, v7;
	Graph<Vertex> g;
	BellmanFord<Vertex> bf;
	ArrayList<Integer> path;

	@Before
	public void setUp() throws Exception {
		v1 = new Vertex(1);
		v2 = new Vertex(2);
		v3 = new Vertex(3);
		v4 = new Vertex(4);
		v5 = new Vertex(5);
		v6 = new Vertex(6);
		v7 = new Vertex(7);

		g = new Graph<Vertex>(true);
		g.addEdge(v1, v2, 2);
		g.addEdge(v1, v3, 1);
		g.addEdge(v2, v3, 2);
		g.addEdge(v2, v5, 1);
		g.addEdge(v2, v6, 1);
		g.addEdge(v3, v4, 1);
		g.addEdge(v3, v5, 3);
		g.addEdge(v4, v7, 3);
		g.addEdge(v5, v7, 2);
	}

	@Test
	public void test() {
		BellmanFord<Vertex> bf = new BellmanFord<Vertex>(g);

		Assert.assertEquals(bf.findPath(null), null);
		Assert.assertEquals(bf.findPathWeight(null), 0);

		bf.shortestPath(v1);

		path = bf.findPath(v7);
		Assert.assertEquals(path.size(), 4);
		Assert.assertEquals(path.get(0), (Integer) 1);
		Assert.assertEquals(path.get(1), (Integer) 3);
		Assert.assertEquals(path.get(2), (Integer) 4);
		Assert.assertEquals(path.get(3), (Integer) 7);
		Assert.assertEquals(bf.findPathWeight(v7), 5);
	}
	
	//TODO: add test for cycle graph

	//TODO: add test for negative cycle graph
	
}
