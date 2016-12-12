package prologTest;

import java.util.Map;
import java.util.Random;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.JPLException;
import org.jpl7.PrologException;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Util;
import org.jpl7.Variable;

public class PrologCalls {

	public static boolean hooked(String x,String Y) {
		boolean res = (new Random().nextDouble()>=0.8) ? true: false; 
		System.out.println("hooked "+res);
		return res;

	}
	public static void main(String argv[]) {
		//loading the file
		String loadFile = "consult('fishing.pl')";
		System.out.println( "loading file : " + (Query.hasSolution(loadFile) ? "succeeded" : "failed"));

		//for (Map<String, Term> mapRes : new Query("caught(X,Y)").allSolutions()) {
		//	System.out.println(mapRes.toString());
		//}

		for (Map<String, Term> mapRes : new Query("victorious(X)").allSolutions()) {
			System.out.println("victorious(X)");
			System.out.println(mapRes.toString());
		}
	}
}
