import java.io.File;
import java.util.*;

public class Main {

  private static Map<Integer, Stack<Character>> stacks = new HashMap<>();

  public static void main(String[] args) {


    File myObj = new File("src/main/resources/input.txt");

    try (Scanner myReader = new Scanner(myObj)) {
      ArrayList<String> lines = new ArrayList<>();

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        lines.add(data);
      }

      for (String line : lines) {
        if (line.contains("1")) {
          break;
        }
        for (int i = 0; i < line.length(); i += 4) {
          int stackIndex = i / 4 + 1;
          Character entry = line.charAt(i + 1);
          if (!entry.equals(' ')) {
            addToStack(stackIndex, entry);
          }
        }
      }

      reverseAllStacks();

      for (int i = 1; i <= 9; i++) {
        System.out.println(i + ": " + stacks.get(i).peek());
      }

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  private static void addToStack(Integer index, Character entry) {
    stacks.computeIfAbsent(index, k -> new Stack<>());
    Stack<Character> stack = stacks.get(index);
    stack.push(entry);
  }

  private static Stack<Character> reverseStack(Stack<Character> oldStack) {
    Stack<Character> newStack = new Stack<>();
    while (!oldStack.empty()) {
      newStack.push(oldStack.pop());
    }
    return newStack;
  }

  private static void reverseAllStacks() {
    int index = 1;
    for (Stack<Character> stack : stacks.values()) {
      stacks.put(index, reverseStack(stack));
      index++;
    }
  }

}
