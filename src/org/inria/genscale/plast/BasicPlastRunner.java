package org.inria.genscale.plast;

import java.io.File;
import java.util.Properties;

import org.inria.genscale.dbscan.api.IHsp;
import org.inria.genscale.dbscan.api.IQueryResult;
import org.inria.genscale.dbscan.api.IRequest;
import org.inria.genscale.dbscan.api.IRequestResult;
import org.inria.genscale.dbscan.api.RequestAdapter;
import org.inria.genscale.dbscan.impl.plast.PlastSystem;

public class BasicPlastRunner extends RequestAdapter{

	public static void main(String[] args)
	{

		/** STEP 1: setup the PLAST job*/
		/* we setup a Properties object with PLAST mandatory arguments: query, subject and method.*/
		Properties props = new Properties();
		props.setProperty (IRequest.QUERY_URI, System.getProperty("user.dir")+"/db/query.fa");
		props.setProperty (IRequest.SUBJECT_URI, System.getProperty("user.dir")+"/db/tursiops.fa");
		props.setProperty (IRequest.ALGO_TYPE, "plastp");
		/*Notice: you can access all PLAST arguments using IRequest interface, as they are available
		 * using PLAST command line tool. For instance, in this basic app, we do not set number of Hits 
		 * and HSPs to report: so, PLAST will report all results.*/

		/* Using PLAST Java API implies that output argument is not required. However, it is highly recommended
		 * to set such a file to help PLAST using temporary files during job execution; otherwise it will create
		 * a temporary file of its own in the current directory.*/
		File fOut = new File(System.getProperty("tmp.dir")+"output.txt");
		props.setProperty (IRequest.OUTPUT_URI,  fOut.getAbsolutePath());

		/* not required, but useful: set the number of cores to use. If not set, PLAST uses all available
		 * cores on the computer.*/
		props.setProperty (IRequest.NB_PROCESSORS,  "4");

		/* PLAST is a bank to bank sequence comparison tool. Its algorithm is made such that hits are not 
		 * reported using query order as they appear in the query file. Using the following argument forces
		 * PLAST to sort hits following query order. Do not care about "1000" value, it is for internal use.
		 */
		props.setProperty(IRequest.FORCE_QUERY_ORDERING, "1000");

		/** STEP 2: create the PLAST request. */

		/* A request is not yet a PLAST job. At this stage, the PLAST engine is only prepared to run a job...*/
		IRequest req = PlastSystem.getRequestManager().createRequest(props);

		/* ... and before starting a job, we need to register a listener. This is the way PLAST will provide
		 * us with results (i.e. hits and HSPs): by way of a IRequestListener */
		BasicPlastRunner pr = new BasicPlastRunner();
		req.addListener(pr);

		/** STEP3: run PLAST job. */
		try {
			req.execute();
		} catch (Exception e) {  
			e.printStackTrace();  
		}

		/** STEP 4: do some cleanup... */
		/* We do not want to keep output file: so, delete it!*/
		fOut.delete();
		/* This is a sample application that is going to terminate now. So removing the listener
		 * from the PLAST engine is not required here, but is is definitively required for real 
		 * applications, such as GUI-based that may run many jobs in the row. Not doing the following
		 * call may results in weird application behavior, including crash!*/
		req.removeListener(pr);
	}

	@Override
	public void requestResultAvailable(IRequest request, IRequestResult result) {
		/* This is the key part of the software: each time this method is called by the PLAST Java/c++
		 * gateway, some results are available. During a single job, PLAST slices the query and report
		 * results for each slice. So this method is usually called several times by PLAST to report
		 * all results, even for small queries.
		 */
		System.out.println ("LISTENER: requestSlice Finished");

		/* a results may contain hits for one or more queries...*/
		while (result.hasNext())
		{
			IQueryResult query = result.next ();
			System.out.println ("\nQUERY   len=" + query.getSequence().getLength() + "  def=" + 
					query.getSequence().getDefinition());

			/*... for each query we may have several hits...*/
			while (query.hasNext())
			{
				org.inria.genscale.dbscan.api.IHit hit = query.next ();
				System.out.println ("   HIT   len=" + hit.getSequence().getLength() + "  def=" + 
						hit.getSequence().getLength());
				/*... for each hit, we may have several HSPs.*/
				while (hit.hasNext())
				{
					IHsp hsp = hit.next ();
					System.out.println ("       HSP  " + hsp);
				}
			}
		}

		/* Advice: adapt the above code to meet your needs on how you'd like to retrieve/print/handle/etc 
		 * PLAST results.*/
	}

}
