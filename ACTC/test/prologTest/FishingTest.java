package prologTest;

import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.JPLException;
import org.jpl7.PrologException;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Util;
import org.jpl7.Variable;

public class FishingTest {

	public static void main(String argv[]) {

		FishingTest test = new FishingTest();
		test.test1();
	
	}
	private void test1(){

		//loading the file
		String loadFile = "consult('fishing.pl')";
		System.out.println( "loading file : " + (Query.hasSolution(loadFile) ? "succeeded" : "failed"));

		System.out.println("Liste des poissons : ");
		for (Map<String, Term> mapRes : new Query("fish(X)").allSolutions()) {
			System.out.println(mapRes.toString());
		}
		boolean res = new Query("fish(tom)").hasSolution() ;
		System.out.println("fish(tom) : "+ res);


	}
}
