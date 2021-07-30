package app.sawmill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ebrahim Kh.
 */


public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Scanner keyboard = new Scanner(System.in);
        List<String> orders = new ArrayList<>();
        String input = "";
        List<Integer> sawmill;
        Integer max = 0;
        Integer caseCounter = 0;
        Integer cycle = 0;
        do {
            try {
                input = keyboard.nextLine();
                sawmill = split(input);
                if (sawmill.size() > 1) {
                    List<List<Integer>> trunks = new ArrayList<>();
                    Integer trunk = sawmill.remove(0);
                    List<Integer> copy = new ArrayList<>(sawmill);
                    trunks.add(copy);

                    for (int i = 0; i < trunks.size(); i++) {
                        ReplyLogic reply = logic(trunks.get(i), 0);
                        max += reply.getMax();
                        orders.add(reply.getOrder());
                    }
                } else {
                    cycle = sawmill.get(0);
                }
                caseCounter += caseCounter++;
            } catch (Exception e) {
            }
        }
        while (!input.equals("0"));
        System.out.println("CASE: " + caseCounter);
        System.out.println("Max Profit:" + max);
        System.out.println("ORDER:" + orders);
        exit(keyboard); // End of program
    }

    private void exit(Scanner keyboard) {
        try {
            keyboard.close();
        } finally {
            System.out.println("Application has been Finished.");
        }
    }

    private static List<Integer> split(String str) {
        return Stream.of(str.split(" "))
            .map(Integer::new)
            .collect(Collectors.toList());
    }


    private static ReplyLogic logic(List<Integer> arr, int k) {
        StringBuilder order = new StringBuilder();
        Integer max = 0;
        for (int i = k; i < arr.size(); i++) {
            java.util.Collections.swap(arr, i, k);
            logic(arr, k + 1);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() - 1) {
            var calculate = calculate(arr);
            if (max < calculate) {
                max = calculate;
            }
            order.append(Arrays.toString(arr.toArray()));
        }
        Map<Integer, String> result = new HashMap<>();
        result.put(max, order.toString());
        return new ReplyLogic(max, order.toString());
    }

    private static Integer point(Integer blocks) {
        switch (blocks) {
            case 1:
                return -1;
            case 2:
                return 3;
            case 3:
                return 1;
        }
        return null;
    }

    private static Integer calculate(List<Integer> possibleOrder) {
        boolean firstMoreThanThreeBlocks = true;
        Integer result = 0;
        for (int i = 0; i < possibleOrder.size(); i++) {
            var trunk = possibleOrder.get(i);
            if (trunk == 4) {
                if (firstMoreThanThreeBlocks) {
                    result += (point(3) + point(1));
                    firstMoreThanThreeBlocks = false;
                } else {
                    result += (point(2) + point(2));
                }
            } else if (trunk > 4) {
                result += point(trunk);
            } else {
                // i dont know what should i do
            }

        }
        return result;
    }

    public static class ReplyLogic {
        private Integer max;
        private String order;

        public ReplyLogic(Integer max, String order) {
            this.max = max;
            this.order = order;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
    }


}
