import java.io.File;
import java.util.*;

public class Main {

  private static final Map<Integer, Stack<Character>> stackMapOne = new HashMap<>();
  private static final Map<Integer, Stack<Character>> stackMapTwo = new HashMap<>();
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
        if (line.contains("move")) {
          moves.add(getMove(line));
        }
        if (lines.contains("move") || line.contains("1")) {
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
      for (Stack<Character> stack : stackMapOne.values()) {
        builderOne.append(stack.peek());
      }
      System.out.println("Part one: " + builderOne.toString());

      for (String move : moves) {
        partTwoMove(move);
      }

      StringBuilder builderTwo = new StringBuilder();
      for (Stack<Character> stack : stackMapTwo.values()) {
        builderTwo.append(stack.peek());
      }
      System.out.println("Part two: " + builderTwo);

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  private static void addToStack(Integer index, Character entry) {
    stackMapOne.computeIfAbsent(index, k -> new Stack<>());
    Stack<Character> stack = stackMapOne.get(index);
    stack.push(entry);
    stackMapTwo.computeIfAbsent(index, k -> new Stack<>());
    Stack<Character> stackBackup = stackMapTwo.get(index);
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
    for (Stack<Character> stack : stackMapOne.values()) {
      stackMapOne.put(index, reverseStack(stack));
      index++;
    }
    index = 1;
    for (Stack<Character> stack : stackMapTwo.values()) {
      stackMapTwo.put(index, reverseStack(stack));
      index++;
    }
  }

  private static void partOneMove(String move) {
    int numToMove = getNumMove(move);
    int from = getFrom(move);
    int to = getTo(move);
    for (int i = 1; i <= numToMove; i++) {
      moveFromStackAtoB(stackMapOne.get(from), stackMapOne.get(to));
    }
  }

  private static void partTwoMove(String move) {
    int numToMove = getNumMove(move);
    int from = getFrom(move);
    int to = getTo(move);
    if (numToMove == 1) {
      for (int i = 1; i <= numToMove; i++) {
        moveFromStackAtoB(stackMapTwo.get(from), stackMapTwo.get(to));
      }
    } else {
      Stack<Character> tempStack = new Stack<>();
      for (int i = 0; i < numToMove; i++) {
        moveFromStackAtoB(stackMapTwo.get(from), tempStack);
      }
      for (int i = 0; i < numToMove; i++) {
        moveFromStackAtoB(tempStack, stackMapTwo.get(to));
      }
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
