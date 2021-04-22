import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Class main
 */
public class Main {


    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        // Load Transitions from transitions repository
        List<Transaction> transactions = loadTransactions();
        //List<Transaction> transactions = loadTransactionsParallel();

        // Load products prices from product repository
        loadTransationsPrix(transactions);
        //loadTransationsPrixParallel(transactions);
        // Print products that have the best sales
        // and those that generate the highest turnover per store
        printByHour(transactions);
        long endTime = System.nanoTime();
        long durationInNano = (endTime - startTime);  //Total execution time in nano seconds
        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);  //Total execution time in nano seconds
        System.out.println("durationInNano : "+durationInNano);
        System.out.println("durationInMillis : "+ durationInMillis);
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static List<String> loadLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static Stream<String> loadLinesParallel(String path) throws IOException {
        return Files.lines(Paths.get(path)).parallel();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private static List<Transaction> loadTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        List<String> lines = loadLines("transactions_20150114.txt");
        lines.stream().forEach(l -> {
            Transaction t = TransitionUtils.createTransition(l.split("[|]"));
            transactions.add(t);
        });
        return transactions;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private static List<Transaction> loadTransactionsParallel() throws IOException {
        List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
        Stream<String> lines = loadLinesParallel("transactions_20150114.txt");
        lines.forEach(l ->
                transactions.add(TransitionUtils.createTransition(l.split("[|]")))
        );
        return transactions;
    }

    /**
     *
     * @param transactions
     * @throws IOException
     */
    private static void loadTransationsPrix(List<Transaction> transactions)
            throws IOException {
        List<String> lines = loadLines("reference_prod-8e588f2f-d19e-436c-952f-1cdd9f0b12b0_20150114.txt");
        Map<String, String> produitAndPrice = new HashMap<>();
        lines.stream().forEach(l -> {
            String[] item = l.split("[|]");
            produitAndPrice.put(item[0], item[1]);
        });
        transactions.stream().forEach(t -> {
            t.setPrix(Double.parseDouble(produitAndPrice.getOrDefault(t.getIdProduit(),"0")));
        });
    }

    /**
     *
     * @param transactions
     * @throws IOException
     */
    private static void loadTransationsPrixParallel(List<Transaction> transactions)
            throws IOException {
        Map<String, String> productPrice = new HashMap<>();
        Stream<String> lines = loadLinesParallel("reference_prod-8e588f2f-d19e-436c-952f-1cdd9f0b12b0_20150114.txt");
        lines.forEach(l ->
                productPrice.put(l.split("[|]")[0], l.split("[|]")[1])
        );
        transactions.stream().forEach(t -> {
            t.setPrix(Double.parseDouble(productPrice.getOrDefault(t.getIdProduit(), "0")));
        });
    }

    /**
     *
     * @param transactions
     */
    private static void printByHour(List<Transaction> transactions) {
        Map<String, List<Transaction>> hourMap = new TreeMap<>();
        for (Transaction transaction : transactions) {
            String hour = transaction.getDate().substring(0, 11);
            hourMap.computeIfAbsent(hour, k -> new ArrayList<>())
                    .add(transaction);
        }
        hourMap.forEach((k, v) -> {
        	System.out.println("Date : "+k);
        	v.stream().sorted().forEach(t -> {
				System.out.println("____Product ID : "+t.getIdProduit()+"  __ Turnover: "+t.getQuantite()*t.getPrix()+" euros");
			});
			System.out.println("********************************************************************");
		});

    }

}