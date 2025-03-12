package com.company.demo.view.action;

import io.jmix.flowui.view.StandardCloseAction;

// tag::myCloseAction[]
public class MyCloseAction extends StandardCloseAction {

    private final String result;

    public MyCloseAction(String result) {
        super("myCloseAction");

        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
// end::myCloseAction[]
