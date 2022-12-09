import javax.print.DocFlavor;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

  private static Map<Integer, Stack<Character>> stacks = new HashMap<>();
  private static ArrayList<String> moves = new ArrayList<>();

  public static void main(String[] args) {


    File myObj = new File("src/main/resources/input.txt");

    try (Scanner myReader = new Scanner(myObj)) {
      ArrayList<String> lines = new ArrayList<>();

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        lines.add(data);
      }

      for (String line : lines) {
        if (line.contains(" 1   2   3   4   5   6   7   8   9")) {
          continue;
        }
        if (line.contains("move")) {
          moves.add(getMove(line));
          continue;
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

      for (String move : moves) {
        completeMove(move);
      }

      StringBuilder builder = new StringBuilder();
      for (Stack<Character> stack : stacks.values()) {
        builder.append(stack.peek());
      }
      System.out.println(builder.toString());

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

  private static void completeMove(String move) {
    int numToMove;
    int from;
    int to;
    if (move.length() < 4) {
      numToMove = Integer.parseInt(move.substring(0, 1));
      from = Integer.parseInt(move.substring(1, 2));
      to = Integer.parseInt(move.substring(2, 3));
    } else {
      numToMove = Integer.parseInt(move.substring(0, 2));
      from = Integer.parseInt(move.substring(2, 3));
      to = Integer.parseInt(move.substring(3, 4));
    }
    for (int i = 1; i <= numToMove; i++) {
      moveFromStackAtoB(stacks.get(from), stacks.get(to));
    }
  }

  private static void moveFromStackAtoB(Stack<Character> a, Stack<Character> b) {
    Character target = a.pop();
    b.push(target);
  }

  private static String getMove(String line) {
    return line
            .replace("move", "")
            .replace("from", "")
            .replace("to", "")
            .replace(" ", "").trim();
  }

}
