package TryingOutCode.readingPrintingLines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class lines {

    public static void main(String[] args) throws IOException {

        System.out.println("===READING WITH BUFFERED READER===");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a line: ");
        String line = br.readLine();

        System.out.println("===READING WITH SCANNER===");
        Scanner skener = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int broj = skener.nextInt();

        System.out.println("===WRITING WITH PRINT WRITER===");
        PrintWriter writer = new PrintWriter(System.out);
        writer.println("Hello, World!");
        writer.flush();

    }
}
