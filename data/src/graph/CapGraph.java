/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;


/**
 * @author Gaozhiquan Wang
 * 
 *
 */

public class CapGraph extends MultiGraph {
	
	public CapGraph(String id) {
		super(id);
		
		setNodeFactory(new CapNodeFactory());
		
	}

	private HashMap<String, CapNode> nodes = new HashMap<String,CapNode>();

	public void copyNodes(HashMap<String,CapNode> oldNodes) {
		nodes = oldNodes;
	}
	public HashMap<String,CapNode> getNodes(){
		return nodes;
	}
	
	public ArrayList<Node> getNeighbors(Node n){
		ArrayList<Node> res = new ArrayList<Node>();
		for (Node i : this.getNodeSet()) if (n.hasEdgeBetween(i)) res.add(i);
		return res;
	}
	
	public CapNode addNode(String s) {
		CapNode temp = super.addNode(s);
		//temp.addAttribute("ui.label",s);
		temp.addAttribute("layout.weight",0.05);
		nodes.put(s, temp);
		return getNode(s);
	}
	
	public void addEdge(String fromID, String toID){
			String edgeName = fromID + '~' + toID;
			addEdge(edgeName, fromID, toID); 
	}
	
	public Edge computeBet() {
		HashMap<Edge,Double> bet = new HashMap<Edge, Double>();
		bet = bfs();
		Edge toD = null;
		for (Edge e :bet.keySet()) {
			if (toD == null || bet.get(e) > bet.get(toD)) toD = e;
		}
		System.out.println(toD);
		return toD;
	}
	
	// bfs all the nodes
	private HashMap<Edge, Double> bfs() {
		HashMap<String, ArrayList<ArrayList<Edge>>> shortPath = new HashMap<String, ArrayList<ArrayList<Edge>>>(); 
		HashMap<Edge, Double> bet = new HashMap<Edge, Double>();
		//each Node as source vertex
		for (Node n : this.getNodeSet()) {
			Integer step = 0; 
			Queue<Node> queue = new LinkedList<Node>();
			HashMap<Node, Integer> reached= new HashMap<Node, Integer>();
			queue.add(n);
			ArrayList<ArrayList<Edge>> temp = new ArrayList<ArrayList<Edge>>();
			ArrayList<Edge> emp = new ArrayList<Edge>();
			temp.add(emp);
			shortPath.put(n.getId()+ '-' +n.getId(), temp);
			reached.put(n, 0);
			while(!queue.isEmpty()) {
				//System.out.println("queue");
				//System.out.println(queue);
				Integer qsize = queue.size();
				//bfs for each step
				for (int i = 0; i<qsize; i++) {
					//first time reach
					if(reached.get(queue.peek())>=step)
					{
						Node curr = queue.poll();
						// traverse each neighbor node
						for(Node next : getNeighbors(curr)) {
							//not reached or reached at the same step as this attempt
							if (!reached.containsKey(next) || reached.get(next)>step) {
								reached.put(next, step+1);
								if(!queue.contains(next)) queue.add(next);
									ArrayList<ArrayList<Edge>> addOn =
										updatePath(curr, next, shortPath.get(n.getId()+ '-' + curr.getId()));
									//if no current available shortest path, add to the existing pathlist
									if(shortPath.containsKey(n.getId()+ '-' + next.getId())) {
										ArrayList<ArrayList<Edge>> former = 
											shortPath.get(n.getId()+ '-' + next.getId());
										for (ArrayList<Edge> l : addOn) former.add(l);
										shortPath.put(n.getId()+ '-' + next.getId(), former);
									}
									else shortPath.put(n.getId()+ '-' + next.getId(), addOn); 
							}
						}
					}
				}
				step++;
			}
		}
		for (Node n: this.getNodeSet()) addBet(n, shortPath, bet);
		return bet;
	}
	
	// add betweenness for each source node
	public void addBet(Node from, HashMap<String, ArrayList<ArrayList<Edge>>> shortPath, HashMap<Edge, Double> bet) {
		for (Node n: this.getNodeSet()) {
			if (n==from || !shortPath.containsKey(from.getId()+ '-' + n.getId())) continue;
			ArrayList<ArrayList<Edge>> pathlist = shortPath.get(from.getId()+ '-' + n.getId());
			for (ArrayList<Edge> path : pathlist) {
				for (Edge e: path) {
					if (!bet.containsKey(e)) bet.put(e, 1.000 / pathlist.size());
					else bet.put(e, bet.get(e)+1.000/pathlist.size());
				}
			}
		}
		return;
	}
	
	// append target node to previous node's shortest path list
	private ArrayList<ArrayList<Edge>> 
	updatePath(Node pre, Node target, ArrayList<ArrayList<Edge>> lPath){
		ArrayList<ArrayList<Edge>> res = new ArrayList<ArrayList<Edge>>();
		for (ArrayList<Edge> n : lPath) {
			ArrayList<Edge> temp = new ArrayList<Edge>();
			for (Edge e : n) temp.add(e);
			temp.add(pre.getEdgeBetween(target));
			res.add(temp);
		}
		return res;
	}

}
