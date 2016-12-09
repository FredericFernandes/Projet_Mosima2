package prologTest.prologLib2;

/**
 * 
 * One lib to execute (theoretically) bidirectional Java and prolog calls
 * 
 * https://github.com/jiprolog/jiprolog/wiki
 * 
 */

import java.io.IOException;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;

public class SynchronousQuery {
	// main
	public static void main(String args[])
	{
		// New instance of prolog engine
		JIPEngine jip = new JIPEngine();

		JIPTerm queryTerm = null;

		// parse query
		try
		{
			// consult file
			jip.consultFile("ressources/prolog/test/fishing.pl");//familyRelationships.pl

			queryTerm = jip.getTermParser().parseTerm("fish(tom)");//father(Father, Child)
		}
		catch(JIPSyntaxErrorException ex)
		{
			ex.printStackTrace();
			System.exit(0); // needed to close threads by AWT if shareware
		}

		// open Query
		JIPQuery jipQuery = jip.openSynchronousQuery(queryTerm);
		JIPTerm solution;

		// Loop while there is another solution
		while (jipQuery.hasMoreChoicePoints())
		{
			solution = jipQuery.nextSolution();
			System.out.println(solution);

			JIPVariable[] vars = solution.getVariables();
			for (JIPVariable var : vars) {
				if (!var.isAnonymous()) {
					System.out.print(var.getName() + " = " + var.toString(jip) + " ");
					System.out.println();
				}
			}
		}
	}
}
