package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class VaadinGui extends VerticalLayout{

    DemoApp demoApp;
    @Autowired
    public VaadinGui(DemoApp demoApp) {

        NumberField number = new NumberField("Number: ");
        Button button = new Button("Show result: ");
        Label label = new Label("Squered power number");
        button.addClickListener(click -> {
            label.setText(String.valueOf(demoApp.getSquaredPower(number.getValue())));

        });
        add(number, button, label);
    }
}
