/**
 * Class utils
 */
public class TransitionUtils {

    public static Transaction createTransition(String... values) {
        Transaction t = new Transaction();
        t.setTxtId(values[0]);
        t.setDate(values[1]);
        t.setMagasin(values[2]);
        t.setIdProduit(values[3]);
        t.setQuantite(Integer.parseInt(values[4]));
        return t;
    }
}
