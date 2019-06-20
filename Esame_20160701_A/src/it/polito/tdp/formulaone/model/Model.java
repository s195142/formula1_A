package it.polito.tdp.formulaone.model;

import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Driver;

public class Model {
	
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	
	public Model() {
	}

	public List<Integer> getAllYears() {
		FormulaOneDAO dao = new FormulaOneDAO();
		return dao.getAllYearsOfRace();
	}
	
	public String migliorPilota(Integer anno) {
		String result = "";
		double max = 0.0;
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		FormulaOneDAO dao = new FormulaOneDAO();
		dao.creaGrafo(grafo, anno);
		
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			System.out.println(grafo.getEdgeWeight(e)+"\n");
		}
		
		for(Driver dr : grafo.vertexSet()) {
			double vittorie = 0.0;
			for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(dr)) {
				vittorie+=grafo.getEdgeWeight(edge);
			}
			for(DefaultWeightedEdge edge : grafo.incomingEdgesOf(dr)) {
				vittorie-=grafo.getEdgeWeight(edge);
			}
			
			if(vittorie>max) {
				max = vittorie;
				result = "Il miglior pilota e' "+dr+" con un risultato di "+vittorie+"\n";
			}
		}
		
		return result;
	}


}
