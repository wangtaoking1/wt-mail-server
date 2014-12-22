package com.wt.smtp.receive;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class HeloStateTest {

    @Test
    public void testCheckEmpty() {
        HeloState state = new HeloState();
        Assert.assertTrue(state.checkEmpty(" "));
        Assert.assertTrue(state.checkEmpty("  "));
        Assert.assertTrue(state.checkEmpty("    "));
    }

}
