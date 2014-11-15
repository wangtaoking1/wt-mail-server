package com.wt.utils.mx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.xbill.DNS.*;

/**
 * @author wangtao
 * @time 2014/11/15
 */
public class MXExchanger {
	/**
	 * Look up the mx records
	 * @param server
	 * @return "The sorted mx records"
	 * @throws Exception
	 */
	public static MXRecord mxLookup(String server) throws Exception{
		
		ArrayList<MXRecord> mxServers = new ArrayList<MXRecord>();
		
		Lookup lookUpAgent = new Lookup(server, Type.MX);
		Record[] records = lookUpAgent.run();
		
		for (int i = 0; i < records.length; i++) {
			mxServers.add((MXRecord)records[i]);
		}
		
		Collections.sort(mxServers, new MXCompare());
		return mxServers.get(0);
	}
	
}
