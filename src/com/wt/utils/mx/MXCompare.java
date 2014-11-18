package com.wt.utils.mx;

import java.util.Comparator;
import org.xbill.DNS.MXRecord;

public class MXCompare implements Comparator {
    @Override
    public int compare(Object arg0, Object arg1) {
        return Integer.compare(((MXRecord)arg0).getPriority(), ((MXRecord)arg1).getPriority());
    }
    
}
