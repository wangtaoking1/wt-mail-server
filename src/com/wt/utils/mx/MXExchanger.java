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
    public static ArrayList<MXRecord> mxLookup(String server) throws Exception{
        
        ArrayList<MXRecord> mxServers = new ArrayList<MXRecord>();
        
        Lookup lookUpAgent = new Lookup(server, Type.MX);
        Record[] records = lookUpAgent.run();
        
        for (int i = 0; i < records.length; i++) {
            mxServers.add((MXRecord)records[i]);
        }
        
        Collections.sort(mxServers, new MXCompare());
        return mxServers;
    }
    
    /**
     * 
     * @param server
     * @return "The best mx record from the sorted mx records"
     * @throws Exception
     */
    public static String getMxServer(String server) throws Exception {
        ArrayList<MXRecord> servers = MXExchanger.mxLookup(server);
        if (servers.isEmpty())
            return null;
        
        //delete the last char '.' in the name of the mx server
        String sname = servers.get(0).getTarget().toString();
        return sname.substring(0, sname.length() - 1);
    }
    
}
