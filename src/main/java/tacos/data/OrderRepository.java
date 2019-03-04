package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;

import java.util.List;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
    /*
    Example of customized find method
    Will provide following SQL result:
    SELECT * FROM ORDER WHER CITY=?;

    Another possible keywords in method name are:
        IsAfter, After, IsGreaterThan, GreaterThan
        IsGreaterThanEqual, GreaterThanEqual
        IsBefore, Before, IsLessThan, LessThan
        IsLessThanEqual, LessThanEqual
        IsBetween, Between
        IsNull, Null
        IsNotNull, NotNull
        IsIn, In
        IsNotIn, NotIn
        IsStartingWith, StartingWith, StartsWith
        IsEndingWith, EndingWith, EndsWith
        IsContaining, Containing, Contains
        IsLike, Like
        IsNotLike, NotLike
        IsTrue, True
        IsFalse, False
        Is, Equals
        IsNot, Not
        IgnoringCase, IgnoresCase, AllIgnoringCase, AllIgnoresCase
     */
    List<Order> findByCity(String city);

    /*
    Custom query example using JPQL syntax (case-sensitive ! according to the entities)
    Method name is irrelevant
     */
    @Query("SELECT o FROM Order o WHERE city='Krakow'")
    List<Order> findAllFromKrakow();

}
