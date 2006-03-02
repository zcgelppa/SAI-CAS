/**
 * Sai_casSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package sai_cas.gen_services;
import org.apache.log4j.Logger;

import sai_cas.services.MainAxisServices;


public class Sai_casSoapBindingImpl implements sai_cas.gen_services.MainAxisServices
{
    static Logger logger = Logger.getLogger("sai_cas.AXIS_SERVICES");
	public void insertCatalogfromURI(java.lang.Object uriCatalog) throws java.rmi.RemoteException
	{
    }

    public void insertCatalog(java.lang.String catalogString) throws java.rmi.RemoteException
    {
    	logger.info("Running insertCatalog...");
		logger.debug("The following catalogue has been inserted" + catalogString);

    	try 
    	{
    		sai_cas.services.MainAxisServices.insertCatalog(catalogString);
    	}
    	catch (Exception e)
    	{
    		logger.error("Catched exception ",e);
    	}
    }
    public java.lang.String[] getCatalogNames() throws java.rmi.RemoteException
    {
    	try 
    	{
    		return MainAxisServices.getCatalogNames();
    	}
    	catch (Exception e)
    	{
    		logger.error("Catched exception ",e);
    		return null;
    	}
    }
}
