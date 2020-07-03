package io.github.frc5024.libkontrol;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.frc5024.libkontrol.statemachines.StateMachine;
import io.github.frc5024.libkontrol.statemachines.StateMetadata;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRegularUsage {

    private enum SystemStates {
        IDLE, SCANNING, POINTING
    }

    private StateMachine<SystemStates> sm;

    // Logging captures
    private String logName;
    private String logOutput;

    public TestRegularUsage() {

        // Create a StateMachine for use during tests
        sm = new StateMachine<>("TestStateMachine");
        sm.setConsoleHook((c, m) -> {
            logName = c;
            logOutput = m;
        });

    }

    private void runScan(StateMetadata<SystemStates> m) {

    }

    @Test
    public void a_testStateConfiguration() {

        // Perform a state addition
        sm.addState(SystemStates.SCANNING, this::runScan);

        // Ensure this action was correctly logged
        assertEquals("StateSetLogName", "Added state: SCANNING", logOutput);
        assertEquals("StateSetLogComponent", "TestStateMachine", logName);

        // Add the second state
        sm.addState(SystemStates.POINTING, (m) -> {
        });
    }

    @Test
    public void b_testDefaultState() {

        sm.setDefaultState(SystemStates.IDLE, (m) -> {
        });
    }

}