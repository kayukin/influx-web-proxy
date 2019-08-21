package com.kayukin.arduinoproxy.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class SensorsDataTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(SensorsData.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}