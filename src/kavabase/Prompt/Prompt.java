package kavabase.Prompt;

import java.util.Scanner;

public class Prompt {

    public static final String PROMPT = "kavasql> ";

    public static void run() {
        QueryExecutor queryExecutor = new QueryExecutor();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("kavasql> ");
            String input = scanner.next();
            while (!input.endsWith(";")) {
                input = input + " " + scanner.next();
            }
            String[] query = input.replaceAll(System.lineSeparator(), " ")
                                  .replaceAll(" +", " ")
                                  .toLowerCase()
                                  .split(";");
            for (String currentQuery : query) {
                queryExecutor.execute(currentQuery.trim());
            }
        }
    }
}
