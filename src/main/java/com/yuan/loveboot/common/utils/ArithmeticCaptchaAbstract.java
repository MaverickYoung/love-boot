package com.yuan.loveboot.common.utils;

import com.wf.captcha.base.Captcha;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 算术表达式生成器，用于创建简单算术表达式及其计算结果
 * <p>完全独立实现运算逻辑，不依赖JavaScript引擎，支持加减乘运算</p>
 *
 * @author Maverick
 */
@Setter
public abstract class ArithmeticCaptchaAbstract extends Captcha {
    private String arithmeticString;  // 计算公式

    public ArithmeticCaptchaAbstract() {
        setLen(2);
    }

    /**
     * 生成算术表达式及其计算结果
     * <p>表达式格式示例：3x2+1=?，计算结果为7</p>
     *
     * @return 表达式计算结果的字符数组形式
     * @see #evaluateExpression(String) 实际计算逻辑实现
     */
    @Override
    protected char[] alphas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(num(10));  // 添加数字
            if (i < len - 1) {
                int type = num(1, 4);
                if (type == 1) {
                    sb.append("+");
                } else if (type == 2) {
                    sb.append("-");
                } else if (type == 3) {
                    sb.append("x");  // 生成时使用x表示乘法
                }
            }
        }

        // 替换x为*并计算结果
        String expression = sb.toString().replace("x", "*");
        int result = evaluateExpression(expression);

        // 构建最终结果
        chars = String.valueOf(result);
        sb.append("=?");
        arithmeticString = sb.toString();
        return chars.toCharArray();
    }

    /**
     * 使用双栈算法计算表达式值
     * <h3>实现特性：</h3>
     * <ul>
     *   <li>支持运算符：+、-、*（生成时使用x表示）</li>
     *   <li>自动处理运算符优先级（乘除优先加减）</li>
     *   <li>仅支持个位数运算</li>
     * </ul>
     *
     * @param expression 有效数学表达式，如"3*2+1"
     * @return 表达式的整数计算结果
     * @throws IllegalArgumentException 当包含无效运算符时抛出
     * @throws ArithmeticException      当发生除零错误时抛出（当前实现未启用除法）
     */
    private int evaluateExpression(String expression) {
        Deque<Integer> numbers = new LinkedList<>();  // 数字栈
        Deque<Character> ops = new LinkedList<>();    // 操作符栈

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                numbers.push(Character.getNumericValue(c));  // 数字直接入栈
            } else {
                // 处理栈顶优先级更高的运算符
                while (!ops.isEmpty() && getPriority(ops.peek()) >= getPriority(c)) {
                    calculate(numbers, ops);
                }
                ops.push(c);  // 当前运算符入栈
            }
        }

        // 处理剩余运算符
        while (!ops.isEmpty()) {
            calculate(numbers, ops);
        }

        return numbers.pop();
    }

    /**
     * 获取运算符优先级
     *
     * @param op 运算符字符，支持 +、-、*、/
     * @return 优先级数值（数值越大优先级越高）
     * @throws IllegalArgumentException 当运算符无效时抛出
     */
    private int getPriority(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> throw new IllegalArgumentException("无效运算符: " + op);
        };
    }

    /**
     * 执行单次二元运算并更新数字栈
     *
     * @param numbers 数字栈（操作数存放位置）
     * @param ops     操作符栈（当前运算符）
     * @throws ArithmeticException 当除数为零时抛出
     */
    private void calculate(Deque<Integer> numbers, Deque<Character> ops) {
        int b = numbers.pop();
        int a = numbers.pop();
        char op = ops.pop();

        switch (op) {
            case '+' -> numbers.push(a + b);
            case '-' -> numbers.push(a - b);
            case '*' -> numbers.push(a * b);
            case '/' -> numbers.push(a / b);
            default -> throw new IllegalArgumentException("无效运算符: " + op);
        }
    }

    public String getArithmeticString() {
        checkAlpha();
        return arithmeticString;
    }

}
