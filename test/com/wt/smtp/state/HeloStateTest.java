package com.wt.smtp.state;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wt.smtp.state.HeloState;

public class HeloStateTest {

    @Test
    public void testCheckEmpty() {
        HeloState state = new HeloState();
        Assert.assertTrue(state.checkEmpty(" "));
        Assert.assertTrue(state.checkEmpty("  "));
        Assert.assertTrue(state.checkEmpty("    "));
    }

}
