package com.isylph.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2025/7/20 21:57
 * @Version 1.0
 */
public class AdvancedChineseSentenceSplitter {
    // 改进的正则表达式：
    // 1. 匹配完整的括号对（包括嵌套括号）
    // 2. 匹配引号内的内容
    // 3. 匹配句子结束符及后续emoji
    static String regex = "\\([^()]*+(?:\\([^()]*+\\)[^()]*)*+\\)" +  // 匹配完整括号对
            "|\"[^\"]*\"" +  // 匹配引号内的内容
            "|[。！？…~～](\\s*\\p{So}+\\s*)?" +  // 匹配结束符和emoji
            "(?![^()]*\\)[^()]*[。！？…~～])" +  // 排除括号内的结束符
            "(?![^\"]*\"[^\" ]*[。！？…~～][^\" ]*\")";  // 排除引号内的结束符


    public static List<String> split(String paragraph) {
        List<String> sentences = new ArrayList<>();
        // 首先处理括号，确保括号内的内容不被拆分
        String bracketRegex = "\\([^()]*+(?:\\([^()]*+\\)[^()]*)*+\\)" +  // 英文括号
                "|（[^（）]*+(?:（[^（）]*+）[^（）]*)*+）";  // 中文括号
        Pattern bracketPattern = Pattern.compile(bracketRegex);
        Matcher bracketMatcher = bracketPattern.matcher(paragraph);

        // 存储括号位置
        List<BracketPair> brackets = new ArrayList<>();
        while (bracketMatcher.find()) {
            brackets.add(new BracketPair(bracketMatcher.start(), bracketMatcher.end()));
        }


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraph);

        int lastEnd = 0;
        StringBuilder currentSentence = new StringBuilder();

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            // 检查结束符是否在括号内
            if (isInsideBracket(end, brackets)) {
                continue; // 跳过括号内的结束符
            }

            // 添加括号前的文本到当前句子
            if (start > lastEnd) {
                currentSentence.append(paragraph, lastEnd, start);
            }

            String match = paragraph.substring(start, end);

            // 如果匹配到括号或引号内容，直接添加到当前句子
            if (match.startsWith("(") || match.startsWith("\"")) {
                currentSentence.append(match);
            }
            // 如果匹配到结束符+emoji，添加到当前句子并结束当前句子
            else {
                currentSentence.append(match);
                sentences.add(currentSentence.toString().trim());
                currentSentence.setLength(0); // 清空当前句子
            }

            lastEnd = end;
        }

        // 添加最后一部分文本
        if (lastEnd < paragraph.length()) {
            currentSentence.append(paragraph.substring(lastEnd));
        }

        // 添加最后一个句子（如果有）
        if (!currentSentence.isEmpty()) {
            String last = currentSentence.toString().trim();
            int size = sentences.size();
            if (isSentenceEnded(last) && last.length() < 10 && size > 0){
                String end  =sentences.remove(size -1);
                sentences.add(end + last);
            }else{
                sentences.add(last);
            }

        }

        return sentences;
    }

    public static boolean isSentenceEnded(String sentence){
        if (StringUtils.isEmpty(sentence)){
            return true;
        }

        // 检查是否以中文结束标点结尾（排除引号内的标点）
        return sentence.matches(".*[!。！？…~.)）](?:\"|”|’|.|。)?$");
    }



    // 检查位置是否在括号内
    private static boolean isInsideBracket(int pos, List<BracketPair> brackets) {
        for (BracketPair bracket : brackets) {
            if (pos > bracket.start && pos < bracket.end) {
                return true;
            }
        }
        return false;
    }

    // 内部类：表示括号对
    private static class BracketPair {
        int start;
        int end;

        public BracketPair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
