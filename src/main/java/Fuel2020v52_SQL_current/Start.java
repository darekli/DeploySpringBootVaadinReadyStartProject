package Fuel2020v52_SQL_current;

import Fuel2020v52_SQL_current.methods.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Start {


    private FuelRepository fuelRepository;

    @Autowired
    public Start(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {




    }
}
