package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
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
        number.setHasControls(true);
        Button button = new Button("Show result: ");
        Label label = new Label("Squared power number");
Label labelFactorial = new Label("Factorial (150 max)");
            button.addClickListener(click -> {
                try {
                    label.setText(String.valueOf(demoApp.getSquaredPower(number.getValue())));
                    labelFactorial.setText(String.valueOf(demoApp.getFactorial(number.getValue())));
                }catch (NullPointerException e){
                    Notification notification = Notification.show("Please input value");
                    notification.setDuration(3000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    add(notification);
            }});
        add(number, button, label,labelFactorial);
    }
}
