package com.mch.philetiptip;

public class LadeStateMachine {
    private int currentState = 0;

    public boolean isRunning() {
        return running;
    }

    private boolean running = false;

    public void initStateMachine() {
        running = false;
        enterState(0);
    }

    public void processStatemachine(){
        if (running){
            checkTransition();
        }
    }

    private void enterState(int state) {
        currentState = state;
        running = true;
    }

    private void leaveState(int state) {
    }

    private void checkTransition(){
        switch (currentState){

        }
    }
}
