import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    File myObj = new File("src/main/resources/input.txt");

    try (Scanner myReader = new Scanner(myObj)) {
      ArrayList<String> lines = new ArrayList<>();

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        lines.add(data);
      }

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

}
