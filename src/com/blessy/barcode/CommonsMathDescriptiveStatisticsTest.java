package com.blessy.barcode;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Ignore;


@Ignore
public class CommonsMathDescriptiveStatisticsTest {
    public static void main(String[] args) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        descriptiveStatistics.addValue(23);
        descriptiveStatistics.addValue(26);
        descriptiveStatistics.addValue(20);
        System.out.println(descriptiveStatistics.getStandardDeviation());
    }
}
