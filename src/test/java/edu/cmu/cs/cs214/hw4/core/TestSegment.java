package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.Arrays;

import static edu.cmu.cs.cs214.hw4.core.FeatureType.CITY;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.N;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.S;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.W;
import static junit.framework.TestCase.assertTrue;

public class TestSegment {

    @Test
    public void testHashCode(){
        Segment seg1 = new Segment(CITY,Arrays.asList(N,S,W),false);
        Segment seg2 = new Segment(CITY,Arrays.asList(N,S,W),true);
        assertTrue(seg1.hashCode() != seg2.hashCode());
    }
}
