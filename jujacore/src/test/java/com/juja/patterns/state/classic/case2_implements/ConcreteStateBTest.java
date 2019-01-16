package com.juja.patterns.state.classic.case2_implements;

public class ConcreteStateBTest extends ConcreteStateTest {

    @Override
    String getPrevName() {
        return "A";
    }

    @Override
    String getName() {
        return "B";
    }

    @Override
    String getNextName() {
        return "C";
    }

    @Override
    Class<? extends State> getPrevStateClass() {
        return ConcreteStateA.class;
    }

    @Override
    State getState(Context context) {
        return new ConcreteStateB(context);
    }

    @Override
    Class<? extends State> getNextStateClass() {
        return ConcreteStateC.class;
    }
}