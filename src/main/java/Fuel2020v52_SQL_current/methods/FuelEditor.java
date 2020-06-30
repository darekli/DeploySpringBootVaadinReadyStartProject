package Fuel2020v52_SQL_current.methods;

import Fuel2020v52_SQL_current.ConfirmDialog;
import Fuel2020v52_SQL_current.Fuel;
import Fuel2020v52_SQL_current.methods.enums.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@SpringComponent
@UIScope
public class FuelEditor extends VerticalLayout implements KeyNotifier {

    private final FuelRepository repository;
//    EntityManagerFactory entityManagerFactory =//todo left or remove is not work anyway
//            Persistence.createEntityManagerFactory("example-unit");
//private FuelRepositoryImpl fuelRepositoryImpl;
    /**
     * The currently edited customer
     */
    private Fuel fuel;
//   private Dates dates;

    /* Fields to edit properties in Customer entity */
    DatePicker tdate = new DatePicker("date");
    ComboBox<Vehicle> vehicle = new ComboBox<>("vehicle", Vehicle.values());
    NumberField km = new NumberField("km");
    NumberField comp = new NumberField("computer board liters");
    NumberField diesel = new NumberField("diesel");
    ComboBox<Dieselfp> dieselfp = new ComboBox<>("diesel full/part", Dieselfp.values());
    NumberField adblue = new NumberField("ad blue");
    ComboBox<Adbluefp> adbluefp = new ComboBox<>("ad blue full/part", Adbluefp.values());
    NumberField gas = new NumberField("gas");
    ComboBox<Gasfp> gasfp = new ComboBox<>("gas full/part", Gasfp.values());
    ComboBox<Payment> payment = new ComboBox<>("payment cash/card", Payment.values());
    TextField notes = new TextField("note");
    NumberField idling = new NumberField("idling");
    NumberField heating = new NumberField("heating");
    //Grid for method find by date only

    HorizontalLayout datesConfirm = new HorizontalLayout();

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    final Button saveConfirm = new Button(
            VaadinIcon.SAFE.create(), event -> {

        ConfirmDialog dialog = new ConfirmDialog("Confirm save/update",
                "Are you sure you want to save/update data?",
                "Save/Update", this::save);
        dialog.open();
    });

    Button cancel = new Button("Cancel");
    //delete

    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    final Button deleteConfirm = new Button(
            VaadinIcon.TRASH.create(), event -> {

        ConfirmDialog dialog = new ConfirmDialog("Confirm delete",
                "Are you sure you want to delete the item?",
                "Delete", this::delete);
        dialog.open();
    });

    HorizontalLayout actions = new HorizontalLayout(saveConfirm, cancel, deleteConfirm);

    Binder<Fuel> binder = new Binder<>(Fuel.class);
    private ChangeHandler changeHandler;

    @Autowired
    public FuelEditor(FuelRepository repository) {

        this.repository = repository;
        //System.out.println("FIND km WITH ID: nr 1: " + repository.findAllBetweenDates(tdate.getValue()).get(0).getKm());
        HorizontalLayout d = new HorizontalLayout(diesel, dieselfp);
        HorizontalLayout ab = new HorizontalLayout(adblue, adbluefp);
        HorizontalLayout g = new HorizontalLayout(gas, gasfp);
        HorizontalLayout ih = new HorizontalLayout(idling, heating);
        add(tdate, vehicle, km, comp, d, ab, g, ih, payment, notes, actions, datesConfirm);

        // bind using naming convention
        binder.bindInstanceFields(this);
        //todo must have!
        binder.forField(km).bind(Fuel::getKm, Fuel::setKm);
        km.setValue(0.0);
        km.setMin(0);
        km.setMax(2500000);
        km.setStep(100);
        binder.forField(comp).bind(Fuel::getComp, Fuel::setComp);
        comp.setValue(0.0);
        comp.setMin(0);
        comp.setMax(100000);
        comp.setStep(100);
        binder.forField(diesel).bind(Fuel::getDiesel, Fuel::setDiesel);
        diesel.setValue(0.0);
        diesel.setMin(0);
        diesel.setMax(2500000);
        diesel.setStep(50);
        binder.forField(adblue).bind(Fuel::getAdblue, Fuel::setAdblue);
        adblue.setValue(0.0);
        adblue.setMin(0);
        adblue.setMax(2500000);
        adblue.setStep(5);
        binder.forField(gas).bind(Fuel::getGas, Fuel::setGas);
        gas.getAutocomplete();
        gas.setValue(0.0);
        gas.setMin(0);
        gas.setMax(2500000);
        gas.setStep(50);
        binder.forField(idling).bind(Fuel::getIdling, Fuel::setIdling);
        idling.getAutocomplete();
        idling.setValue(0.0);
        idling.setMin(0);
        idling.setMax(500);
        idling.setStep(5);
        binder.forField(heating).bind(Fuel::getHeating, Fuel::setHeating);
        heating.getAutocomplete();
        heating.setValue(0.0);
        heating.setMin(0);
        heating.setMax(500);
        heating.setStep(5);
        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editFuel(fuel));
        setVisible(false);
    }

    void delete() {
        repository.delete(fuel);
        changeHandler.onChange();
        Notification.show("Refueling deleted.", 4000, Notification.Position.TOP_CENTER);
    }

    void save() {
        repository.save(fuel);
        changeHandler.onChange();
        Notification.show("Refueling saved.", 4000, Notification.Position.TOP_CENTER);
    }

    public String getAllDieselBetweenDates2(LocalDate dateStart, LocalDate dateEnd) {
        try {
            for (Fuel w : repository.findAll()) {
                double sum = 0;
                for (int i = 0; i < repository.findAll().size(); i++) {
                    sum = sum + w.getDiesel();
                }
                return String.valueOf(sum);
            }

        } catch (NullPointerException e) {

        }
        return "Input Date";
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editFuel(Fuel c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            fuel = repository.findById(c.getId()).get();
        } else {
            fuel = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(fuel);

        setVisible(true);

        // Focus first name initially
        payment.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    /////////D I E S E L //////////////////////////
    public double getFindAllDiesel(LocalDate dateStart, LocalDate dateEnd) {
        double sum = 0.0;
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel diesel : elements) {
            sum += diesel.getDiesel().doubleValue();
        }
        return sum;
    }

    public String getFindAllDieselString(LocalDate dateStart, LocalDate dateEnd) {
        double sum = getFindAllDiesel(dateStart, dateEnd);
        String sumString = String.format("%.3f", sum);
        return String.valueOf(sumString);
    }

    public double getFindFirstKmIsFueledfp_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : elements) {
            if (aaa.getDieselfp().equals(Dieselfp.FULL)) {
                return aaa.getKm();
            }
        }
        return 0;
    }

    public boolean getFindFirstIsFueledFullOrPart(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> fp = repository.findFirstByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : fp) {
            if (aaa.getDieselfp().equals(Dieselfp.FULL)) {
                return true;
            }
        }
        return false;
    }

    public double getFindLastKmIsFueledfp_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> lastKm = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByKmDesc(dateStart, dateEnd);
        for (Fuel aaa : lastKm) {
            if (aaa.getDieselfp().equals(Dieselfp.FULL)) {
                return aaa.getKm();
            }
        }
        return 0;
    }

    public double  getFindAllLitersByKm(LocalDate dateStart, LocalDate dateEnd) {
        double sum = 0.0;
        List<Fuel> sumLiters = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel pid : sumLiters) {
            if (pid.getDieselfp().equals(Dieselfp.FULL)) {
                sum += pid.getDiesel().doubleValue();
                return sum;
            }
        }
        return 0;
    }

    public double getFindFirstLitersByKmToRemove(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : elements) {
            if (aaa.getDieselfp().equals(Dieselfp.FULL)) {
                return aaa.getDiesel();
            }
        }
        return 0;
    }

    public double getAvgFuelConsumption(LocalDate dateStart, LocalDate dateEnd) {
        boolean fullPart = getFindFirstIsFueledFullOrPart(dateStart, dateEnd);
        double kmEnd = getFindLastKmIsFueledfp_FULL(dateStart, dateEnd);
        double kmStart = getFindFirstKmIsFueledfp_FULL(dateStart, dateEnd);
        double sumLiters = getFindAllLitersByKm(dateStart, dateEnd);
        double sumLiters2 = getFindAllDiesel(dateStart, dateEnd);
        double litersToRemove = getFindFirstLitersByKmToRemove(dateStart, dateEnd);
        double avg = (sumLiters2 - litersToRemove) / (kmEnd - kmStart) * 100;

        if (fullPart == false) {
            return 0;
        } else
            return avg;
        }

    public String getAvgFuelConsumptionString(LocalDate dateStart, LocalDate dateEnd) {
        boolean fullPart = getFindFirstIsFueledFullOrPart(dateStart, dateEnd);
        String descr = "Average fuel consumption - uncountable. Check the dates. Set the start position from Fueledfp = FULL";
        String descr2 = "Uncountable";

        double kmEnd = getFindLastKmIsFueledfp_FULL(dateStart, dateEnd);
        double kmStart = getFindFirstKmIsFueledfp_FULL(dateStart, dateEnd);
        double sumLiters = getFindAllLitersByKm(dateStart, dateEnd);
        double sumLiters2 = getFindAllDiesel(dateStart, dateEnd);
        double litersToRemove = getFindFirstLitersByKmToRemove(dateStart, dateEnd);
        double avg = (sumLiters2 - litersToRemove) / (kmEnd - kmStart) * 100;
        String avgString = String.format("%.3f", avg);
        if (fullPart == false) {
            return descr;
        } else if (avg > 0) {
            return "Average fuel consumption: " + avgString + " liters";
        } else {
            return descr2;
        }
    }
    ///////AVG FUEL WITHOUT IDLING & HEATING///////////////////
    public String getAvgFuelConsWithoutIdlingAndHeating(LocalDate dateStart, LocalDate dateEnd) {
            boolean fullPart = getFindFirstIsFueledFullOrPart(dateStart, dateEnd);
            String descr = "Average fuel consumption - uncountable. Check the dates. Set the start position from Fueledfp = FULL";
            String descr2 = "Uncountable";
            double kmEnd = getFindLastKmIsFueledfp_FULL(dateStart, dateEnd);
            double kmStart = getFindFirstKmIsFueledfp_FULL(dateStart, dateEnd);
            double sumLiters2 = getFindAllDiesel(dateStart, dateEnd);
            double litersToRemove = getFindFirstLitersByKmToRemove(dateStart, dateEnd);
        double idling = getIdlingDieselConsumption(dateStart, dateEnd);
        double heating = getHeatingDieselConsumption(dateStart, dateEnd);
            double avg = (sumLiters2 - litersToRemove-idling-heating) / (kmEnd - kmStart) * 100;
            String avgString = String.format("%.3f", avg);
            if (fullPart == false) {
                return descr;
            } else if (avg > 0) {
                return "Pure average fuel consumption without idling & heating: " + avgString + " liters";
            } else {
                return descr2;
            }
        }

    ///////J C FIOLET BONUS///////////////////
    public String getBonusJCF(LocalDate dateStart, LocalDate dateEnd) {
        boolean fullPart = getFindFirstIsFueledFullOrPart(dateStart, dateEnd);
        double kmEnd = getFindLastKmIsFueledfp_FULL(dateStart, dateEnd);
        double kmStart = getFindFirstKmIsFueledfp_FULL(dateStart, dateEnd);
        double sumLiters2 = getFindAllDiesel(dateStart, dateEnd);
        double litersToRemove = getFindFirstLitersByKmToRemove(dateStart, dateEnd);
        double avg = (sumLiters2 - litersToRemove) / (kmEnd - kmStart) * 100;
        if (avg > 0 && avg < 31) {
            return "Bonus: 150.00 £ ";
        } else if (avg >= 31 && avg < 32) {
            return "Bonus: 125.00 £ ";
        } else if (avg >= 32 && avg < 33) {
            return "Bonus: 100.00 £ ";
        } else if (avg >= 33 && avg < 34) {
            return "Bonus: 75.00 £ ";
        } else {
            return "No Bonus";
        }
    }

    //////////SELECTED DISTANCE KM///////////////////////////////////
    public int getSelectedDistanceStart(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> startKm = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : startKm) {
            if (aaa.getKm() != null) {
                return aaa.getKm().intValue();
            }
        }
        return 0;
    }

    public int getSelectedDistanceEnd(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> endKm = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByKmDesc(dateStart, dateEnd);
        for (Fuel aaa : endKm) {
            if (aaa.getKm() != null) {
                return aaa.getKm().intValue();
            }
        }
        return 0;
    }

    public String getSelectedDistanceDuration(LocalDate dateStart, LocalDate dateEnd) {
        double kmEnd = getSelectedDistanceEnd(dateStart, dateEnd);
        double kmStart = getSelectedDistanceStart(dateStart, dateEnd);
        double distance = kmEnd - kmStart;
        return String.valueOf(distance);
    }
    ////////// I D L I N G ///////////////////////////////////

    public double getFindIdlingSum(LocalDate dateStart, LocalDate dateEnd) {
        try {
            double sum = 0.0;
            List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
            for (Fuel idl : elements) {
                sum += idl == null ? 0.0 : idl.getIdling().doubleValue();
            }
            return sum;
        } catch (NullPointerException e) {

        }
        return 0;
    }
    public double getIdlingDieselConsumption(LocalDate dateStart, LocalDate dateEnd) {
        double heatingHours = getFindIdlingSum( dateStart,  dateEnd);
        double sum = 2.5*heatingHours;
        return sum;
    }

    //////////H E A T I N G ///////////////////////////////////

    public double getFindHeatingSum(LocalDate dateStart, LocalDate dateEnd) {
        try {
            double sum = 0.0;

            List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
            for (Fuel h : elements) {
                sum += h == null ? 0.0 : h.getHeating();
            }
            return sum;
        } catch (NullPointerException e) {

        }
        return 0;
    }
    public double getHeatingDieselConsumption(LocalDate dateStart, LocalDate dateEnd) {
        double heatingHours = getFindHeatingSum( dateStart,  dateEnd);
        double sum = 0.25*heatingHours;
        return sum;
    }


    //////////////A D B L U E    EDITOR//////////////////////////////////////
    public boolean getFindFirstIsAdBlueFullOrPart(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> fp = repository.findFirstByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : fp) {
            if (aaa.getAdbluefp().equals(Adbluefp.FULL)) {
                return true;
            }
        }
        return false;
    }

    public double getFindAllAdBlue(LocalDate dateStart, LocalDate dateEnd) {
        double sum = 0.0;

        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel adb : elements) {
            sum += adb.getAdblue().doubleValue();
        }
        return sum;
    }

    public String getFindAllAdBlueString(LocalDate dateStart, LocalDate dateEnd) {
        double sum = getFindAllAdBlue(dateStart, dateEnd);
        String sumString = String.format("%.3f", sum);
        return String.valueOf(sumString);
    }

    public double getFindFirstKmIsAdBlue_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : elements) {
            if (aaa.getAdbluefp().equals(Adbluefp.FULL)) {
                return aaa.getKm();
            }
        }
        return 0;
    }

    public double getFindLastKmIsAdBlue_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> lastKm = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByKmDesc(dateStart, dateEnd);
        for (Fuel aaa : lastKm) {
            if (aaa.getAdbluefp().equals(Adbluefp.FULL)) {
                return aaa.getKm();
            }
        }
        return 0;
    }

    public double getFindFirstAdBlueByKmToRemove(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        for (Fuel aaa : elements) {
            if (aaa.getAdbluefp().equals(Adbluefp.FULL)) {
                return aaa.getAdblue();
            }
        }
        return 0;
    }

    public String getAvgAdBlueConsumption(LocalDate dateStart, LocalDate dateEnd) {

        boolean fullPart = getFindFirstIsAdBlueFullOrPart(dateStart, dateEnd);
        String descr = "Average AdBlue consumption - uncountable. Check the dates. Set the start position from Fueledfp = FULL";
        String descr2 = "Uncountable";

        double kmEnd = getFindLastKmIsAdBlue_FULL(dateStart, dateEnd);
        double kmStart = getFindFirstKmIsAdBlue_FULL(dateStart, dateEnd);
        double sumLiters2 = getFindAllAdBlue(dateStart, dateEnd);
        double litersToRemove = getFindFirstAdBlueByKmToRemove(dateStart, dateEnd);
        double avg = (sumLiters2 - litersToRemove) / (kmEnd - kmStart) * 100;
        String avgString = String.format("%.3f", avg);
        if (fullPart == false) {
            return descr;
        } else if (avg > 0) {
            return "Average AdBlue consumption: " + avgString + " l";
        } else {
            return descr2;
        }
    }
    //////////////GAS LPG/CNG  EDITOR//////////////////////////////////////
    public boolean getFindFirstIsGasFullOrPart(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> fp = repository.findFirstByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);

        for (Fuel aaa : fp) {
            if (aaa.getGasfp().equals(Gasfp.FULL)) {
                return true;
            }
        }
        return false;
    }

    public double getFindAllGas(LocalDate dateStart, LocalDate dateEnd) {
        double sum = 0.0;

        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        try {
            for (Fuel adb : elements) {
                sum += adb.getGas().doubleValue();
            }
            return sum;
        } catch (NullPointerException e) {

        }
        return 0;
    }
    public String getFindAllGasString(LocalDate dateStart, LocalDate dateEnd) {
        double sum = getFindAllGas(dateStart, dateEnd);
        String sumString = String.format("%.3f", sum);
        return String.valueOf(sumString);
    }

    public double getFindFirstKmIsGas_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        try {
            for (Fuel aaa : elements) {
                if (aaa.getGasfp().equals(Gasfp.FULL)) {
                    return aaa.getKm();
                }
            }
        } catch (NullPointerException e) {

        }
        return 0;
    }
    public double getFindLastKmIsGas_FULL(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> lastKm = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByKmDesc(dateStart, dateEnd);
        try {
            for (Fuel aaa : lastKm) {
                if (aaa.getGasfp().equals(Gasfp.FULL)) {
                    return aaa.getKm();
                }
            }
        } catch (NullPointerException e) {

        }return 0;
    }
    public double getFindFirstGasByKmToRemove(LocalDate dateStart, LocalDate dateEnd) {
        List<Fuel> elements = repository.findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(dateStart, dateEnd);
        try {
        for (Fuel aaa : elements) {
            if (aaa.getGasfp().equals(Gasfp.FULL)) {
                return aaa.getGas();
            }
        }
        } catch (NullPointerException e) {

        }return 0;
    }

    public String getAvgGasConsumption(LocalDate dateStart, LocalDate dateEnd) {

        boolean fullPart = getFindFirstIsGasFullOrPart(dateStart, dateEnd);
        String descr = "Average AdBlue consumption - uncountable. Check the dates. Set the start position from Fueledfp = FULL";
        String descr2 = "Uncountable";

        double kmEnd = getFindLastKmIsGas_FULL(dateStart, dateEnd);
        double kmStart = getFindFirstKmIsGas_FULL(dateStart, dateEnd);
        double sumLiters2 = getFindAllGas(dateStart, dateEnd);
        double litersToRemove = getFindFirstGasByKmToRemove(dateStart, dateEnd);
        double avg = (sumLiters2 - litersToRemove) / (kmEnd - kmStart) * 100;
        String avgString = String.format("%.3f", avg);
        if (fullPart == false) {
            return descr;
        } else if (avg > 0) {
            return "Average Gas consumption: " + avgString + " l";
        } else {
            return descr2;
        }
    }
}
