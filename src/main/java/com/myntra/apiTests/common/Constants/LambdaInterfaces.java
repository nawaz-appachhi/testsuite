package com.myntra.apiTests.common.Constants;

import com.myntra.commons.exception.ManagerException;
import org.codehaus.jettison.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Shubham Gupta on 3/11/17.
 */
public class LambdaInterfaces {

        /* Lambda functional interfaces that are available for use:
            **1. BiConsumer: accepts two input arguments & returns none **
            **2. BiFunction: accepts two arguments & produces a result.**
            3. BinaryOperator: Accepts two operands of the same type, producing a result of the same type as the operands
            **4. BiPredicate: take two operands and produces one boolean **
            ** 5. Consumer: accepts a single input argument & returns none **
            6. DoubleBinaryOperator: Represents an operation upon two {@code double}-valued oper&s & producing a {@code double}-valued result.
            7. DoubleConsumer: accepts a single {@code double}-valued argument & returns no result.
            8. DoubleFunction: accepts a double-valued argument & produces a result.
            9. DoublePredicate: Accept double & produces one boolean result.
            10. DoubleSupplier: Takes nothing but returns double.
            11. DoubleToIntFunction: Takes double & returns int.
            12. DoubleToLongFunction: Takes double & returns long.
            13. DoubleUnaryOperator: Takes double & return double.
            ** 14. Function:  accepts one argument & produces a result. **
            15. IntBinaryOperator: Takes two int & returns one int.
            16. IntConsumer: Accept single int & produces nothing.
            17. IntFunction: Takes one int & return result.
            18. IntPredicate: take single int & produces boolean.
            19. IntSupplier: take nothing & produces int.
            20. IntToDoubleFunction: take int & ret double
            21. IntToLongFunction: take int & ret long
            22. IntUnaryOperator: take int & ret int
            23. LongBinaryOperator: take two long & ret long
            24. LongConsumer: take one long & ret none
            25. LongFunction: take one long & ret result
            26. LongPredicate: takes long & ret boolean
            27. LongSupplier: takes none & ret long
            28. LongToDoubleFunction: takes long & ret double
            29. LongToIntFunction: takes long & ret int
            30. LongUnaryOperator: take long & ret long
            31. ObjDoubleConsumer: take obj & double
            32. ObjIntConsumer: takes obj & int
            33. ObjLongConsumer: take obj & long
            ** 34. Predicate: takes one param & ret boolean **
            ** 35. Supplier: take none & ret result **
            36. ToDoubleBiFunction: take two params & produce double
            37. ToDoubleFunction: take one param & produce double
            38. ToIntBiFunction: take two param & produce int
            39. ToIntFunction: take one param & ret int
            ** 40. ToLongBiFunction: take two parama & ret long **
            41. ToLongFunction: take one param & ret long
            ** 42. UnaryOperator: single operand that produces a result of the same type as its operand.** */



    public interface Function<T, R>{
        R apply(T t) throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException;
    }

    public interface BiFunction<T,U,R>{
        R apply(T t, U u) throws IOException, JAXBException, InterruptedException, ManagerException, XMLStreamException, JSONException;
    }

    public interface TriFunction<T, U, V, R>{
        R apply (T t, U u, V v) throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException;
    }

    public interface FourFunction<T, U, V, W, R>{
        R apply (T t, U u, V v, W w) throws UnsupportedEncodingException, JAXBException;
    }

    public interface FiveFunction<T, U, V, W, X,R>{
        R apply (T t, U u, V v, W w, X x);
    }

    public interface SixFunction<T, U, V, W, X, Y, R>{
        R apply (T t, U u, V v, W w, X x, Y y);
    }

    public interface SevenFunction<S,T, U, V, W, X, Y, R>{
        R apply (S s,T t, U u, V v, W w, X x, Y y);
    }

    public interface EightFunction<P,S,T, U, V, W, X, Y, R>{
        R apply (P p,S s,T t, U u, V v, W w, X x, Y y);
    }


    public interface MultiFunction<T>{
        T ret (MultiFunction<?> ... o);
    }

    public interface Consumer<T>{
        void accept (T t) throws IOException, InterruptedException, XMLStreamException, JSONException, JAXBException, ManagerException;
    }

    public interface BiConsumer<T, U>{
        void accept (T t, U u) throws IOException;
    }

    public interface TriConsumer<T, U, V>{
        void accept (T t, U u, V v) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException;
    }

    public interface FourConsumer<T, U, V, W>{
        void accept (T t, U u, V v, W w);
    }

    public interface FiveConsumer<T, U, V, W, X>{
        void accept (T t, U u, V v, W w, X x);
    }

    public interface SixConsumer<T, U, V, W, X, Y>{
        void accept (T t, U u, V v, W w, X x, Y y);
    }

    public interface Supplier<T> {
        T get() throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException;
    }

    public interface SimpleCall{
         void call();
    }

}
