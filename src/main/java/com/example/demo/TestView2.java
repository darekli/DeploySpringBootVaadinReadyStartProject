package com.example.demo;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("testView2")
public class TestView2 extends VerticalLayout {
    public TestView2() {
       H1 h1test = new H1("TestView");
       add(h1test);
    }
}
