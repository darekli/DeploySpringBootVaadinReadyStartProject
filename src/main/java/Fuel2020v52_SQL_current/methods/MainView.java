package Fuel2020v52_SQL_current.methods;

import Fuel2020v52_SQL_current.Fuel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.StringUtils;

@Route
@Theme(value = Lumo.class)
//@PWA(name = "Refueling App", shortName = "Refueling's")
public class MainView extends VerticalLayout {
    Fuel fuel;

    private final FuelRepository repo;

    private final FuelEditor editor;

    final Grid<Fuel> grid;

    final TextField filter;

    private final Button addNewBtn;
    private Button buttonAvgDiesel = new Button("Avg diesel");
    private Button buttonAvgAdBlue = new Button("Avg adBlue");
    H4 h4FuelCons = new H4("Select dates:");
    DatePicker dateStart = new DatePicker("Date From:");
    DatePicker dateEnd = new DatePicker("Date To:");
    Label labelfindSumOfFuelDieselMethod = new Label();
    Label labelfindSumOfFuelDieselBetweenDates = new Label();
    Label labelFueledDTotal = new Label();
    Label labelAvgDCons = new Label();
    Label labelJCF = new Label();
    Label labelAdBlue = new Label();
    Label labelSelectedDistKm = new Label();
    Label labelIdlConsump = new Label();
    Label labelHeatingConsum = new Label();
    Label labelABTotal = new Label();
    Label labelAvgGasCons = new Label();
    Label labelfindSumOfFuelGasMethod = new Label();
    Label labelFueledGasTotal = new Label();
    Label labelfindSumOfFuelGasBetweenDates = new Label();
    Label labelIdlingDCons = new Label();
    Label labelHeatingDCons = new Label();
    Label labelConsWithoutHeatNIdling = new Label();
    H6 h6D = new H6();
    H6 h6AB = new H6();
    H6 h6Gas = new H6();
    H6 h6Idl = new H6();
    H6 h6aHeat = new H6();
    H6 h6Bonus = new H6();
    H6 h6Dist = new H6();


    public MainView(FuelRepository repo, FuelEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Fuel.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Main Menu", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(grid, actions, editor);
        HorizontalLayout dateFieldsFromTo = new HorizontalLayout(dateStart, dateEnd);
        HorizontalLayout buttons = new HorizontalLayout(buttonAvgDiesel, buttonAvgAdBlue);
        add(h4FuelCons, dateFieldsFromTo, buttons);
        add(h6D, labelAvgDCons, labelConsWithoutHeatNIdling, h6Bonus, labelJCF, labelFueledDTotal, h6Dist, labelSelectedDistKm, h6Idl, labelIdlConsump, labelIdlingDCons, h6aHeat, labelHeatingConsum, labelHeatingDCons, h6AB, labelAdBlue, labelABTotal, h6Gas, labelAvgGasCons, labelfindSumOfFuelGasMethod, labelFueledGasTotal, labelfindSumOfFuelGasBetweenDates);
//Button buttonShowTable = new Button("Show table");
//buttonShowTable.addClickListener(event -> {
//    grid.addColumn(c-> c.getId()).setHeader("Id");
//    grid.addColumn(c-> c.getKm()).setHeader("Kilometers");
//});
//add(buttonShowTable);
        //labelAvgGasCons, labelfindSumOfFuelGasMethod, labelFueledGasTotal, labelfindSumOfFuelGasBetweenDates
        grid.setHeight("300px");
        //todo remember about this line below!
        grid.setColumns("id", "tdate", "vehicle", "km", "comp", "diesel", "dieselfp", "adblue", "adbluefp", "gas", "gasfp", "payment", "notes", "idling", "heating");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        filter.setPlaceholder("Filter by Notes");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listFuelings(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editFuel(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        //todo option below is working as well (addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));)
        //addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
        addNewBtn.addClickListener(e -> editor.editFuel(new Fuel(editor.tdate.getValue(), editor.vehicle.getValue(), editor.km.getValue(), editor.comp.getValue(), editor.diesel.getValue(), editor.dieselfp.getValue(), editor.adblue.getValue(), editor.adbluefp.getValue(), editor.gas.getValue(), editor.gasfp.getValue(), editor.payment.getValue(), editor.notes.getValue(), editor.idling.getValue(), editor.heating.getValue())));
        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listFuelings(filter.getValue());
        });

        // Initialize listing
        listFuelings(null);
        ///////////////////////////////////////////////////////////////////
//        buttonMyFind2.addClickListener(e -> editor.myFind());
//        add(buttonMyFind2);

//        buttonMyFind2.addClickListener(e -> {
//            labelfindSumOfFuelDieselMethod.setText(editor.findSumOfFuelDieselMethod());

        //});

        //System.out.println("findAllByFueledfp_Full "+repo.findAllByFueledfp_Full());
//        System.out.println("////////");
//        repo.findMYByPayment("CARD").forEach(System.out::println);
        // repo.findMYByDates("2020.05.01","2020.05.20").forEach(System.out::println);
//        System.out.println("////////");
//        System.out.println(" repo.findAll().forEach(System.out::println); Below:");
//        // repo.findFuelByLiters(fuel.getLiters()).forEach(System.out::println);//todo not work
//        repo.findFuelByFueledfp(Fueledfp.FULL).forEach(System.out::println);//todo is ok
//        System.out.println("------1------");
//        repo.findFuelByFueledfpAndPaymant(Fueledfp.FULL, Paymant.CASH).forEach(System.out::println);//todo is ok

//        System.out.println("--------2!!----");
//        repo.findAllByTdateGreaterThanAndTdateIsLessThanEqualAndFueledfpIsNot(LocalDate.of(2020, 05, 01), LocalDate.of(2020, 05, 21), Fueledfp.PART);
        // System.out.println("FIND ALL " + repo.findAll());
        //System.out.println("editor.getFindWholeLiters() " + editor.getFindWholeLiters());
        //todo that methods must be in quick Listner becouse is giving empty list []
        //System.out.println("Whole liters @Query "+ repo.findWholeLiters(dateStart.getValue(),dateEnd.getValue()));
        //System.out.println("DATE,DATE: "+fuelDao.getSelectAdrByUnAndPackGroup(dateStart.getValue(),dateEnd.getValue()));
        //System.out.println("tadate "+repo.findAllByTdate(tdate.getValue()));
        Label labelError404 = new Label();

        buttonAvgDiesel.addClickListener(e -> {
            try {
                h6D.setText("DIESEL:");
                labelFueledDTotal.setText("Fueled up diesel total: " + editor.getFindAllDieselString(dateStart.getValue(), dateEnd.getValue()) + " liters");
                labelAvgDCons.setText(editor.getAvgFuelConsumptionString(dateStart.getValue(), dateEnd.getValue()));
                labelConsWithoutHeatNIdling.setText(editor.getAvgFuelConsWithoutIdlingAndHeating(dateStart.getValue(), dateEnd.getValue()));
                h6AB.setText("AD BLUE:");
                labelAdBlue.setText(editor.getAvgAdBlueConsumption(dateStart.getValue(), dateEnd.getValue()));
                labelABTotal.setText("Fueled up Ad Blue total. Time selected: " + editor.getFindAllAdBlueString(dateStart.getValue(), dateEnd.getValue()) + " liters");
                h6Gas.setText("GAS:");
                labelAvgGasCons.setText((editor.getAvgGasConsumption(dateStart.getValue(), dateEnd.getValue())));
                labelFueledGasTotal.setText("Fueled up Gas total. Time selected: " + editor.getFindAllGasString(dateStart.getValue(), dateEnd.getValue()) + " liters");
                h6Bonus.setText("BONUS:");
                labelJCF.setText(editor.getBonusJCF(dateStart.getValue(), dateEnd.getValue()));
                h6Dist.setText("DISTANCE:");
                labelSelectedDistKm.setText("Distance: " + editor.getSelectedDistanceDuration(dateStart.getValue(), dateEnd.getValue()) + " km");
                h6Idl.setText("IDLING:");
                labelIdlConsump.setText("Idling: " + editor.getFindIdlingSum(dateStart.getValue(), dateEnd.getValue()) + " H");
                labelIdlingDCons.setText("Idling by fuel consumption: " + editor.getIdlingDieselConsumption(dateStart.getValue(), dateEnd.getValue()) + " liters");
                h6aHeat.setText("HEATING:");
                labelHeatingConsum.setText("Heating: " + editor.getFindHeatingSum(dateStart.getValue(), dateEnd.getValue()) + " H");
                labelHeatingDCons.setText("Heating by fuel consumption: " + editor.getHeatingDieselConsumption(dateStart.getValue(), dateEnd.getValue()) + " liters");


//grid.addColumn(c->c.getKm()).setHeader("Kilometers");
                add();
                //todo ok but empty data and makes problems                labelAvgGasCons.setText(editor.getAvgGasConsumption(dateStart.getValue(),dateEnd.getValue()));
//                labelFueledGasTotal.setText(editor.getAvgGasConsumption(dateStart.getValue(), dateEnd.getValue()));
            } catch (NullPointerException d) {
                Notification notification = Notification.show(
                        "Fields can not be empty");
                notification.setDuration(3000);
                notification.setPosition(Notification.Position.MIDDLE);
            } catch (InvalidDataAccessApiUsageException f) {
                Notification notification = Notification.show(
                        "Fields can not be empty");
                notification.setDuration(3000);
                notification.setPosition(Notification.Position.MIDDLE);
            }

        });

//        } catch (InvalidDataAccessApiUsageException e) {
//
//        } catch (IllegalArgumentException e) {
//
//        }
        try {

            buttonAvgAdBlue.addClickListener(e -> {

            });
        } catch (InvalidDataAccessApiUsageException e) {

        } catch (IllegalArgumentException e) {

        } catch (NullPointerException e) {

        }
    }

    // tag::listCustomers[]
    void listFuelings(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByNotesStartsWithIgnoreCase(filterText));
        }
    }
}
