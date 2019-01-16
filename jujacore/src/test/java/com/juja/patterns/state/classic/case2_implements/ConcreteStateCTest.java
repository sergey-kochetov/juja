package com.juja.patterns.state.classic.case2_implements;

public class ConcreteStateCTest extends ConcreteStateTest {

    @Override
    String getPrevName() {
        return "B";
    }

    @Override
    String getName() {
        return "C";
    }

    @Override
    String getNextName() {
        return "A";
    }

    @Override
    Class<? extends State> getPrevStateClass() {
        return ConcreteStateB.class;
    }

    @Override
    State getState(Context context) {
        return new ConcreteStateC(context);
    }

    @Override
    Class<? extends State> getNextStateClass() {
        return ConcreteStateA.class;
    }
}