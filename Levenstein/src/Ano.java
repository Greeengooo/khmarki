import java.lang.reflect.Array;
import java.util.*;

public class Ano {
    /*
    * I - insertion
    * D - deletion
    * R - replacement
    * */
private static int[][] cost;
private static char[][] ops;
private static List <String> output;
    public static int computeLevenshtein(String A, String B) {
        for (int j = 0; j < lenA + 1; j++) {
            cost[j][0] = j;
            ops[j][0] = 'D';
        }
        cost = new int[lenA + 1][lenB + 1];
        ops = new char[lenA + 1][lenB + 1];
        output = new ArrayList<>();

        for (int i = 0; i < lenB + 1; i++) {
            cost[0][i] = i;
            ops[0][i] = 'I';
        }
        for (int j = 0; j < lenA + 1; j++) {
            cost[j][0] = j;
            ops[j][0] = 'D';
        }


        for (int i = 1; i < lenA + 1; i++) {
            for(int j = 1; j < lenB + 1; j++) {
                int match = (A.charAt(i - 1) == B.charAt(j - 1)) ? 0 : 1;
                int cost_replace = cost[i-1][j - 1] + match;
                int cost_insert  = cost[i][j - 1] + 1;
                int cost_delete  = cost[i - 1][j] + 1;

                cost[i][j] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
                if (cost[i][j] == cost_replace)
                    ops[i][j] = 'R';
                else if (cost[i][j] == cost_insert)
                    ops[i][j] = 'I';
                else if (cost[i][j] == cost_delete)
                    ops[i][j] = 'D';
            }
        }

        //print table
        System.out.print("    ");
        for (int i = 0; i < B.length(); i++)
            System.out.print(B.charAt(i) + " ");
        System.out.println();

        for (int i = 0; i < lenA + 1; i++) {
            if (i > 0)
                System.out.print(A.charAt(i - 1) + " ");
            else
                System.out.print("  ");
            for (int j = 0; j < lenB + 1; j++) {
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }
        return cost[lenA][lenB];
    }

    public static void getEdits(int[][] cost, String A, String B) {
        int i = cost.length - 1;
        int j = cost[0].length - 1;
        while (i != 0 && j != 0) {
            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                output.add(printWord(i, A) + " -> " + printWord(j, B) + " (" + Ano.cost[i][j] + ") " + ops[i][j]);
                i = i - 1;
                j = j - 1;
            } else if (cost[i][j] == cost[i - 1][j - 1] + 1) {
                output.add(printWord(i, A) + " -> " + printWord(j, B) + " (" + Ano.cost[i][j] + ") " + ops[i][j]);
                i = i - 1;
                j = j - 1;
            } else if (cost[i][j] == cost[i - 1][j] + 1) {
                output.add(printWord(i, A) + " -> " + printWord(j, B) + " (" + Ano.cost[i][j] + ") " + ops[i][j]);
                i = i - 1;
            } else if (cost[i][j] == cost[i][j - 1] + 1) {
                output.add(printWord(i, A) + " -> " + printWord(j, B) + " (" + Ano.cost[i][j] + ") " + ops[i][j]);
                j = j - 1;
            } else {
                throw new IllegalArgumentException("Something wrong with given data");
            }
        }
        output.add(printWord(i, A) + " -> " + printWord(j, B) + " (" + Ano.cost[i][j] + ") " + ops[i][j]);
    }

    private static String printWord(int m, String word) {
        String res = "";
        for (int i = 0; i < m; i++) {
            res += word.charAt(i);
        }
        return res;
    }

    private static void output() {
        Collections.reverse(output);
        for ( String el :output) {
            System.out.println(el);
        }
    }

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("S1: ");
        String A = in.nextLine();
        System.out.print("S2: ");
        String B = in.nextLine();
        System.out.println("s1 = " + A);
        System.out.println("s2 = " + B);
        System.out.println("result: " + computeLevenshtein(A, B));
        getEdits(cost, A, B);
        output();
    }
}