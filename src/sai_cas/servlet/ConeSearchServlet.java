package sai_cas.servlet;
import java.io.*;
//import java.util.*;
//import java.sql.*;
//import javax.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sai_cas.db.*;
import sai_cas.vo.*;

public class ConeSearchServlet extends HttpServlet {
	
	public class ConeSearchServletException extends Exception
	{
		ConeSearchServletException()
		{
			super();
		}
		ConeSearchServletException(String s)
		{
			super(s);
		}

	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, java.io.IOException
	{

		PrintWriter out = response.getWriter();
		
		String cat = request.getParameter("cat");
		String tab = request.getParameter("tab");
		
		String sra = request.getParameter("RA");
		String sdec = request.getParameter("DEC");
		String ssr = request.getParameter("SR");


		String format = request.getParameter("format");
		if (format == null)
		{
			format="votable";
		}

		if (format.equals("votable"))
		{
			response.setContentType("text/xml");
		}
		else if (format.equals("csv"))
		{
			response.setContentType("text/csv");
		}
		else 
		{
			response.setContentType("text/plain");
		}


		String sverbosity = request.getParameter("VERB");
		/* We set up the the default maximal verbosity */
		int verbosity;
		try 
		{
			verbosity = Integer.parseInt(sverbosity);
			if ((verbosity < 1) || (verbosity > 3))
			{
				throw new Exception();
			}
		}
		catch (Exception e) 
		{
			verbosity = 3;
		}
		
		String columnListAsString = request.getParameter("columns");
		if (columnListAsString==null)
		{
			columnListAsString = request.getParameter("COLUMNS");
		}

		String withDistance = request.getParameter("withdist");
		if (withDistance == null)
		{
			withDistance = request.getParameter("WITHDIST");
		}
		boolean withDistanceFlag;
		try 
		{
			withDistanceFlag = (Integer.parseInt(withDistance)!=0);
		} 
		catch(Exception e)
		{
			withDistanceFlag = false;
		}

		String columnList[] = null;
		if (columnListAsString!=null)
		{
			columnList = columnListAsString.split(",");
		}


		if (sra == null)
		{
			sra = request.getParameter("ra");
		}
		if (sdec == null)
		{
			sdec = request.getParameter("dec");
		}
		if (ssr == null) 
		{
			ssr = request.getParameter("sr");
		}
		
		ConeSearch cs = new ConeSearch(out, format);

		try 
		{
			double ra, dec, sr;
			
			if ((sra == null) || (sdec == null) || (ssr == null))
			{
				throw new ConeSearchServletException("ERROR: RA, DEC and SR must be defined");
			}
				
			if (cat == null)
			{
				throw new ConeSearchServletException("ERROR: Catalog name must be defined");
			}
						
			ra = Double.parseDouble(sra);
			dec = Double.parseDouble(sdec);
			sr = Double.parseDouble(ssr);

			if (cs.initConeSearch(cat, tab, ra, dec, sr))
			{

				response.setHeader("Content-Disposition",
					"attachment; filename=" + cat + "." +
					(tab == null ? "" : tab) + "_" + sra + "_" +
					sdec + "_" + ssr);
				
				if (columnList == null)
				{
					cs.setVerbosity(verbosity);
				}
				else
				{
					cs.setColumnList(columnList, withDistanceFlag);
				}
				cs.printConeSearch();
			}
		}	
		catch (NumberFormatException e) 
		{
			cs.printConeSearchError("ERROR: Invalid input for (RA,DEC,SR)");
		}
		catch (ConeSearchServletException e) 
		{
			cs.printConeSearchError(e.getMessage());
		}
	}
}
