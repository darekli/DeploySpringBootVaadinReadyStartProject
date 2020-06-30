package Fuel2020v52_SQL_current;

import Fuel2020v52_SQL_current.methods.enums.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fuel_app_dieselabgas")

public class Fuel {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", unique = true, nullable = false)
private Long id;
    private LocalDate tdate;
    @Enumerated(EnumType.STRING)
    Vehicle vehicle;
    private Double km;
    private Double comp;
    private Double diesel;
    @Enumerated(EnumType.STRING)
    Dieselfp dieselfp;
    private Double adblue;
    @Enumerated(EnumType.STRING)
    Adbluefp adbluefp;
    private Double gas;
    @Enumerated(EnumType.STRING)
    Gasfp gasfp;
    @Enumerated(EnumType.STRING)
    Payment payment;
    private String notes;
    private Double idling;
    private Double heating;


    protected Fuel() {
    }

    public Fuel(LocalDate tdate, Vehicle vehicle, Double km, Double comp, Double diesel, Dieselfp dieselfp, Double adblue, Adbluefp adbluefp, Double gas, Gasfp gasfp, Payment payment, String notes, Double idling, Double heating) {
        this.tdate = tdate;
        this.vehicle = vehicle;
        this.km = km;
        this.comp = comp;
        this.diesel = diesel;
        this.dieselfp = dieselfp;
        this.adblue = adblue;
        this.adbluefp = adbluefp;
        this.gas = gas;
        this.gasfp = gasfp;
        this.payment = payment;
        this.notes = notes;
        this.idling = idling;
        this.heating = heating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTdate() {
        return tdate;
    }

    public void setTdate(LocalDate tdate) {
        this.tdate = tdate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Double getComp() {
        return comp;
    }

    public void setComp(Double comp) {
        this.comp = comp;
    }

    public Double getDiesel() {
        return diesel;
    }

    public void setDiesel(Double diesel) {
        this.diesel = diesel;
    }

    public Dieselfp getDieselfp() {
        return dieselfp;
    }

    public void setDieselfp(Dieselfp dieselfp) {
        this.dieselfp = dieselfp;
    }

    public Double getAdblue() {
        return adblue;
    }

    public void setAdblue(Double adblue) {
        this.adblue = adblue;
    }

    public Adbluefp getAdbluefp() {
        return adbluefp;
    }

    public void setAdbluefp(Adbluefp adbluefp) {
        this.adbluefp = adbluefp;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public Gasfp getGasfp() {
        return gasfp;
    }

    public void setGasfp(Gasfp gasfp) {
        this.gasfp = gasfp;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getIdling() {
        return idling;
    }

    public void setIdling(Double idling) {
        this.idling = idling;
    }

    public Double getHeating() {
        return heating;
    }

    public void setHeating(Double heating) {
        this.heating = heating;
    }

    @Override
    public String toString() {
        return "Fuel{" +
                "id=" + id +
                ", tdate=" + tdate +
                ", vehicle=" + vehicle +
                ", km=" + km +
                ", comp=" + comp +
                ", diesel=" + diesel +
                ", dieselfp=" + dieselfp +
                ", adblue=" + adblue +
                ", adbluefp=" + adbluefp +
                ", gas=" + gas +
                ", gasfp=" + gasfp +
                ", payment=" + payment +
                ", notes='" + notes + '\'' +
                ", idling=" + idling +
                ", heating=" + heating +
                '}';
    }
}
