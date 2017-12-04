package jena;


import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;

public class SparqlForPatient {

	public static void main(String args[]){
		testSquarql();	
	}
	static void testSquarql()
	{
		FileManager.get().addLocatorClassLoader(SparqlForPatient.class.getClassLoader());
		Model model = FileManager.get().loadModel("/Users/rajatkinkhabwala/Downloads/rdf.xml");
		String queryString=
				"PREFIX clinic: <http://www.example.org/clinic#> "	+
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				"SELECT ?Patient" +
				" WHERE { " +
//				" ?treatment  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://edu.stevens.cs548/clinic#/Radiology>" +
				" ?Patient <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://edu.stevens.cs548/clinic#/RadiologyPatient>"+
				" }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		
		try{
			ResultSet results = qexec.execSelect();
			List<String> varNames = results.getResultVars();
			
			System.out.println("Patient");
			
			while(results.hasNext()) {
				
				QuerySolution solution = results.nextSolution();
				for(String varName : varNames) {
				
					RDFNode value = solution.get(varName);
					
					System.out.println(value);
				
				}
			}
			
		}finally{
			qexec.close();
			
		}
		
		
	}
}
