package com.isylph.utils.math;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Formula {

    // 初始默认正括号
    public static Set<Character> operator = new HashSet<>();
    static {
        operator.add('{');
        operator.add('(');
        operator.add('[');
    }

    public static void main(String[] args) {
        //String ss= "3+area*{1+amount*[-4/(8-(-price))+7]}";
        String ss= "(price*(-area))*amount/3+100-area*2";
        Map<String, String> replaceKeysMap = new HashMap<String, String>();
        replaceKeysMap.put("amount", "5.2");
        replaceKeysMap.put("area", "2");
        replaceKeysMap.put("price", "60");
        System.out.println(formulaCalculator(ss,replaceKeysMap));
    }

    /*
     * 一共分两步： 1.中缀表达式转后缀表达式: 从左到右遍历中缀表达式的每一个数字和运算符。 如果数字就输出（即存入后缀表达式）；
     * 如果是右括号，则弹出左括号之前的运算符； 如果优先级低于栈顶运算符，则弹出栈顶运算符，并将当前运算符进栈。 遍历结束后，将栈则剩余运算符弹出。
     * 2.后缀表达式计算结果: 从左到右遍历后缀表达式，遇到数字就进栈，遇到符号，就将栈顶的两个数字出栈运算，运算结果进栈，直到获得最终结果。
     */
    /**
     * @Desc
     * @param formula 传入的公式 例：(price*area)*amount/3
     * @param formulaParametersMap 变量对应的map，key是所有变量，value是变量对应的值
     * @return BigDecimal的具体数值
     */
    public static BigDecimal formulaCalculator(String formula, Map<String, String> formulaParametersMap) {
        // 把公式里面的变量换成对应的值
        for (Map.Entry<String, String> entry : formulaParametersMap.entrySet()) {
            formula = formula.replaceAll(entry.getKey(), entry.getValue());
        }
        // 最后一步处理公式，对于负数的前面补0
        formula = formula.replaceAll("(?<![0-9)}\\]])(?=-[0-9({\\[])", "0") + "#";

        // 初始化两个栈，一个存操作数，一个存操作符
        Stack<BigDecimal> opStack = new Stack<>();
        Stack<Character> otStack = new Stack<>();

        // 整数记录器 当num是多位或者小数点时将 num += formulaChar 知道遇见运算符
        String num = "";
        for (int i = 0; i < formula.length(); i++) {
            // 抽取字符
            char formulaChar = formula.charAt(i);
            // 如果字符是数字，则加这个数字累加到num后面
            if (Character.isDigit(formulaChar) || formulaChar == '.') {
                num += formulaChar;
            }
            // 如果不是数字
            else {
                // 如果有字符串被记录，则操作数入栈，并清空
                if (!num.isEmpty()) {
                    BigDecimal n = new BigDecimal(num);
                    num = "";
                    opStack.push(n);
                }
                // 如果遇上了终结符则退出
                if (formulaChar == '#') {
                    break;
                } else if (formulaChar == '+' || formulaChar == '-') {
                    // 如果遇上了+-
                    // 空栈或者操作符栈顶遇到正括号，则入栈
                    if (otStack.isEmpty() || operator.contains(otStack.peek())) {
                        otStack.push(formulaChar);
                    } else {
                        // 否则一直做弹栈计算，直到空或者遇到正括号为止，最后入栈
                        while (!otStack.isEmpty() && !operator.contains(otStack.peek())) {
                            popAndCalculator(opStack, otStack);
                        }
                        otStack.push(formulaChar);
                    }
                } else if (formulaChar == '*' || formulaChar == '/') {
                    // 如果遇上*/
                    // 空栈或者遇到操作符栈顶是括号，或者遇到优先级低的运算符，则入栈
                    if (otStack.isEmpty() || operator.contains(otStack.peek()) || otStack.peek() == '+' || otStack.peek() == '-') {
                        otStack.push(formulaChar);
                    } else {
                        // 否则遇到*或/则一直做弹栈计算，直到栈顶是优先级比自己低的符号，最后入栈
                        while (!otStack.isEmpty() && otStack.peek() != '+' && otStack.peek() != '-' && !operator.contains(otStack.peek())) {
                            popAndCalculator(opStack, otStack);
                        }
                        otStack.push(formulaChar);
                    }
                } else {
                    // 如果是正括号就压栈
                    if (operator.contains(formulaChar)) {
                        otStack.push(formulaChar);
                    } else {
                        // 反括号就一直做弹栈计算，直到遇到正括号为止
                        char r = getBrace(formulaChar);
                        while (otStack.peek() != r) {
                            popAndCalculator(opStack, otStack);
                        }
                        // 最后弹出正括号
                        otStack.pop();
                    }
                }
            }
        }
        // 将剩下的计算完，直到运算符栈为空
        while (!otStack.isEmpty()) {
            popAndCalculator(opStack, otStack);
        }
        // 返回结果
        return opStack.pop();
    }

    // 两个值的的运算,先出栈的放到运算符的右边
    private static void popAndCalculator(Stack<BigDecimal> opStack, Stack<Character> otStack) {
        BigDecimal op2 = opStack.pop();
        BigDecimal op1 = opStack.pop();
        char ot = otStack.pop();
        BigDecimal res = BigDecimal.ZERO;
        switch (ot) {
            case '+':
                res = op1.add(op2);
                break;
            case '-':
                res = op1.subtract(op2);
                break;
            case '*':
                res = op1.multiply(op2);
                break;
            case '/':
                res = op1.divide(op2, 4, BigDecimal.ROUND_HALF_UP);
                break;
            default :
                break;
        }
        opStack.push(res);
    }

    // 处理括号
    private static char getBrace(char operator) {
        switch (operator) {
            case ')':
                return '(';
            case ']':
                return '[';
            case '}':
                return '{';
            default :
                return '#';
        }
    }

}
