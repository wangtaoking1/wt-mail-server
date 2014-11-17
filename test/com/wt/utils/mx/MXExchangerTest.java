package com.wt.utils.mx;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.xbill.DNS.MXRecord;

public class MXExchangerTest {

	@Test
	public void testMxLookup() {
		ArrayList<MXRecord> servers = null;
		try {
			servers = MXExchanger.mxLookup("163.com");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (MXRecord server : servers) {
			String sname = server.getTarget().toString();
			System.out.println(server.getPriority() + "  " + sname.substring(
					0, sname.length() - 1));
		}
		
	}
	
	@Test
	public void testGetMxServer() {
		String mxServer = null;
		try {
			mxServer = MXExchanger.getMxServer("163.com");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(mxServer);
	}

}
