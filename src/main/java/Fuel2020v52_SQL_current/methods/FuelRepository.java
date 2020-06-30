package Fuel2020v52_SQL_current.methods;

import Fuel2020v52_SQL_current.Fuel;
import Fuel2020v52_SQL_current.methods.enums.Dieselfp;
import Fuel2020v52_SQL_current.methods.enums.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("customer2")
public interface FuelRepository extends JpaRepository<Fuel, Long> {

    List<Fuel> findAll();

    List<Fuel> findAllById(long id);

    List<Fuel> findFuelByDiesel(Double diesel);

    List<Fuel> findFuelByDieselfp(Dieselfp dieselfp);

    List<Fuel> findFuelByDieselfpAndPayment(Dieselfp dieselfp, Payment payment);

    // List<Fuel> findAll(LocalDate dateStart, LocalDate dateEnd);
    List<Fuel> findByNotesStartsWithIgnoreCase(String notes);

    //findAllByTdateGreaterThanAndTdateBefore
    List<Fuel> findAllByTdateGreaterThanAndTdateIsLessThanEqual(LocalDate dateStart, LocalDate dateEnd);

    List<Fuel> findAllByTdateGreaterThanEqualAndTdateIsLessThanEqual(LocalDate dateStart, LocalDate dateEnd);

    List<Fuel> findFirstByTdateGreaterThanAndTdateIsLessThanEqual(LocalDate dateStart, LocalDate dateEnd);//

    List<Fuel> findFirstByTdateGreaterThanEqualAndTdateIsLessThanEqual(LocalDate dateStart, LocalDate dateEnd);

    //List<Fuel>findFirstByTdateGreaterThanAndTdateIsLessThanEqual(LocalDate dateEnd);
    List<Fuel> findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByKmDesc(LocalDate dateStart, LocalDate dateEnd);

    List<Fuel> findAllByTdateGreaterThanEqualAndTdateIsLessThanEqualOrderByTdateDesc(LocalDate dateStart, LocalDate dateEnd);

    //List<Fuel> findFirstByTdateGreaterThanAndTdateIsLessThanEqualBOrderByKm(LocalDate dateStart, LocalDate dateEnd);//
    //List<Fuel>findFirstBy(LocalDate dateEnd);
//List<Fuel>findLastByTdateIsLessThanEqual(LocalDate dateEnd);
    // List<Fuel> findAllByFueledfp_Full();
    List<Fuel> findAllByTdateGreaterThanAndTdateIsLessThanEqualAndDieselfpIsNot(LocalDate dateStart, LocalDate dateEnd, Dieselfp dieselfp);
    //List<Fuel> findAllByTdateGreaterThanAndTdateIsLessThanEqualAnndPaymantIs(LocalDate dateStart, Fueledfp fueledfp);//todo ok
    //List<Fuel> findAllByTdateGreaterThanAndTdateAndFueledfpStartsWithFueledfp_FullIsLessThanEqual(LocalDate dateStart, LocalDate dateEnd,Fueledfp fueledfp);
//List<Fuel>findAllByLitersAndTdateGreaterThanAndTdateIsLessThanEqual(Double liters,LocalDate dateStart, LocalDate dateEnd);


    List<Fuel> findAllByPayment(Payment payment);

////W a s acctive/////////////////
//    @Query(value = "select *from customer2.customer2 where tdate between 20200501 and 20200520 ", nativeQuery = true)
//    List<Fuel> findAllBetweenDates(LocalDate tdate2);
//
//    @Query(value = "select  sum(customer2.liters )as total from  customer2.customer2 where tdate  between 20200501 and 20200510 ", nativeQuery = true)
//    Collection<Fuel> findSumOfFuelsDiesel();//km z 22/08 100567km//todo this query is ok
//
//    @Query(value = "SELECT * FROM customer2.customer2 u WHERE u.tdate >= u.tdate = :status", nativeQuery = true)
//// //todo work's with 2 param is giving null insted ;(
//    List<Fuel> findwholeFuelsbetweenDAtes(@Param("status") LocalDate dateStart);//, @Param("name")LocalDate dateEnd
//
//
//    @Query(value = "SELECT * FROM customer2.customer2 u WHERE tdate = u.tdate =:dateStart  and tdate = u.tdate = :dateEnd", nativeQuery = true)
//    List<Fuel> findwholeFuelsbetweenDAtes2(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);//todo not error but not workl
//
//    @Query(value = "SELECT * from customer2.customer2 where paymant =:payment", nativeQuery = true)
//    List<Fuel> findMYByPayment(@Param("payment") String payment);
////W a s acctive/////////////////


//    @Query(value = "SELECT * from customer2.customer2 where (customer2.customer2.diesel)", nativeQuery = true)
//    List<Fuel> findSumLietrs(Double diesel);
//    @Query(value = "SELECT max(liters =:liters) from customer2.customer2", nativeQuery = true)
//    List<Fuel> findMYLiters2(@Param("liters")Double liters);


}

