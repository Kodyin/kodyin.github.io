package graph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;


public class CapNodeFactory implements NodeFactory<CapNode>{

	@Override
	public CapNode newInstance(String id, Graph graph) {
		return new CapNode((AbstractGraph) graph,id);
	
	}

}
