import java.io.File;
import java.util.*;

public class Main {

  private static final Map<Integer, Stack<Character>> stacksOne = new HashMap<>();
  private static Map<Integer, Stack<Character>> stacksTwo = new HashMap<>();
  private static final ArrayList<String> moves = new ArrayList<>();

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
        partOneMove(move);
      }

      StringBuilder builderOne = new StringBuilder();
      for (Stack<Character> stack : stacksOne.values()) {
        builderOne.append(stack.peek());
      }
      System.out.println("Part one: " + builderOne.toString());

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  private static void addToStack(Integer index, Character entry) {
    stacksOne.computeIfAbsent(index, k -> new Stack<>());
    Stack<Character> stack = stacksOne.get(index);
    stack.push(entry);
    stacksTwo.computeIfAbsent(index, k -> new Stack<>());
    Stack<Character> stackBackup = stacksTwo.get(index);
    stackBackup.push(entry);
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
    for (Stack<Character> stack : stacksOne.values()) {
      stacksOne.put(index, reverseStack(stack));
      index++;
    }
    index = 1;
    for (Stack<Character> stack : stacksTwo.values()) {
      stacksTwo.put(index, reverseStack(stack));
      index++;
    }
  }

  private static void partOneMove(String move) {
    int numToMove = getNumMove(move);
    int from = getFrom(move);
    int to = getTo(move);
    for (int i = 1; i <= numToMove; i++) {
      moveFromStackAtoB(stacksOne.get(from), stacksOne.get(to));
    }
  }

  private static int getNumMove(String move) {
    if (move.length() < 4) {
      return Integer.parseInt(move.substring(0, 1));
    } else {
      return Integer.parseInt(move.substring(0, 2));
    }
  }

  private static int getFrom(String move) {
    if (move.length() < 4) {
      return Integer.parseInt(move.substring(1, 2));
    } else {
      return Integer.parseInt(move.substring(2, 3));
    }
  }

  private static int getTo(String move) {
    if (move.length() < 4) {
      return Integer.parseInt(move.substring(2, 3));
    } else {
      return Integer.parseInt(move.substring(3, 4));
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
