package com.isylph.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/1/19 10:42
 * @Version 1.0
 */
@Slf4j
public class FirstLetterExtractorUtil {


    // 匹配中文字符（简繁通用）的正则
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5\\u4e00-\\u9fff\\u3400-\\u4dbf]");
    // 匹配英文字母的正则
    private static final Pattern ENGLISH_LETTER_PATTERN = Pattern.compile("[a-zA-Z]");
    // 匹配空白字符（用于分割英文单词）
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    // 缓存拼音输出格式，提升批量处理性能
    private static final HanyuPinyinOutputFormat UPPERCASE_PINYIN_FORMAT;
    private static final HanyuPinyinOutputFormat LOWERCASE_PINYIN_FORMAT;

    // 静态代码块初始化拼音格式（无需每次创建）
    static {
        // 大写拼音格式（无声调）
        UPPERCASE_PINYIN_FORMAT = new HanyuPinyinOutputFormat();
        UPPERCASE_PINYIN_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        UPPERCASE_PINYIN_FORMAT.setCaseType(HanyuPinyinCaseType.UPPERCASE);

        // 小写拼音格式（无声调）
        LOWERCASE_PINYIN_FORMAT = new HanyuPinyinOutputFormat();
        LOWERCASE_PINYIN_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        LOWERCASE_PINYIN_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }

    // ---------------------- 新增：繁体转简体工具方法 ----------------------
    /**
     * 繁体中文转换为简体中文
     * @param traditionalChinese 繁体中文内容
     * @return 简体中文内容
     */
    private static String convertTraditionalToSimplified(String traditionalChinese) {
        if (traditionalChinese == null || traditionalChinese.trim().isEmpty()) {
            return "";
        }
        // 调用opencc4j工具类完成简繁转换
        return ZhConverterUtil.toSimple(traditionalChinese);
    }

    // ---------------------- 优化：中文（含繁体）拼音首字母提取 ----------------------
    /**
     * 提取中文（含繁体）拼音首字母（忽略非中文字符，默认返回大写）
     * @param chineseContent 待处理的中文内容（简繁均可，可包含其他字符）
     * @return 中文拼音首字母拼接字符串
     */
    public static String extractChinesePinyinFirstLetters(String chineseContent) {
        return extractChinesePinyinFirstLetters(chineseContent, true);
    }

    /**
     * 提取中文（含繁体）拼音首字母（忽略非中文字符，支持大小写切换）
     * @param chineseContent 待处理的中文内容（简繁均可，可包含其他字符）
     * @param isUppercase 是否返回大写
     * @return 中文拼音首字母拼接字符串
     */
    public static String extractChinesePinyinFirstLetters(String chineseContent, boolean isUppercase) {
        if (chineseContent == null || chineseContent.trim().isEmpty()) {
            return "";
        }

        // 步骤1：先将繁体中文转换为简体中文
        String simplifiedChinese = convertTraditionalToSimplified(chineseContent);
        StringBuilder firstLetters = new StringBuilder();
        char[] charArray = simplifiedChinese.trim().toCharArray();
        // 选择对应的拼音格式
        HanyuPinyinOutputFormat pinyinFormat = isUppercase ? UPPERCASE_PINYIN_FORMAT : LOWERCASE_PINYIN_FORMAT;

        for (char c : charArray) {
            // 仅处理中文字符，忽略其他字符（数字、字母、符号等）
            if (CHINESE_PATTERN.matcher(String.valueOf(c)).matches()) {
                try {
                    // 获取单个汉字的拼音（多音字返回多个，取第一个即可）
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, pinyinFormat);
                    if (pinyinArray != null && pinyinArray.length > 0) {
                        // 提取拼音首字母
                        firstLetters.append(pinyinArray[0].charAt(0));
                    }
                } catch (Exception e) {
                    // 拼音格式异常，忽略该字符
                    log.warn("BadHanyuPinyinOutputFormatCombination", e);
                }
            }
        }

        return firstLetters.toString();
    }

    // ---------------------- 保留：英文单词首字符提取 ----------------------
    /**
     * 提取英文句子中每个单词的首字符（默认返回大写，自动分割单词，忽略非英文字符）
     * @param englishSentence 待处理的英文句子（可包含空格、标点）
     * @return 英文单词首字符拼接字符串
     */
    public static String extractEnglishWordFirstLetters(String englishSentence) {
        return extractEnglishWordFirstLetters(englishSentence, true);
    }

    /**
     * 提取英文句子中每个单词的首字符（支持大小写切换，自动分割单词，忽略非英文字符）
     * @param englishSentence 待处理的英文句子（可包含空格、标点）
     * @param isUppercase 是否返回大写
     * @return 英文单词首字符拼接字符串
     */
    public static String extractEnglishWordFirstLetters(String englishSentence, boolean isUppercase) {
        if (englishSentence == null || englishSentence.trim().isEmpty()) {
            return "";
        }

        // 步骤1：按空白字符分割句子为单词数组（处理多个连续空格）
        String[] wordArray = WHITESPACE_PATTERN.split(englishSentence.trim());

        // 步骤2：遍历每个单词，提取首字符（仅保留英文字母）
        return Arrays.stream(wordArray)
                .filter(word -> !word.isEmpty() && ENGLISH_LETTER_PATTERN.matcher(word).find())
                .map(word -> {
                    // 找到第一个英文字母作为单词首字符
                    char firstChar = ' ';
                    for (char c : word.toCharArray()) {
                        if (ENGLISH_LETTER_PATTERN.matcher(String.valueOf(c)).matches()) {
                            firstChar = c;
                            break;
                        }
                    }
                    return String.valueOf(isUppercase ? Character.toUpperCase(firstChar) : Character.toLowerCase(firstChar));
                })
                .collect(Collectors.joining());
    }

    // ---------------------- 新增：中英文混合内容首字母提取（核心新功能） ----------------------
    /**
     * 提取中英文混合内容的首字符（中文取拼音首字母，英文取单词首字符，默认返回大写）
     * @param mixedContent 待处理的中英文混合内容（可含繁体、标点、空格）
     * @return 混合内容首字符拼接字符串
     */
    public static String extractMixedContentFirstLetters(String mixedContent) {
        return extractMixedContentFirstLetters(mixedContent, true);
    }

    /**
     * 提取中英文混合内容的首字符（中文取拼音首字母，英文取单词首字符，支持大小写切换）
     * @param mixedContent 待处理的中英文混合内容（可含繁体、标点、空格）
     * @param isUppercase 是否返回大写
     * @return 混合内容首字符拼接字符串
     */
    public static String extractMixedContentFirstLetters(String mixedContent, boolean isUppercase) {
        if (mixedContent == null || mixedContent.trim().isEmpty()) {
            return "";
        }

        // 步骤1：处理繁体转简体，统一文本格式
        String simplifiedContent = convertTraditionalToSimplified(mixedContent);
        StringBuilder resultBuilder = new StringBuilder();

        // 步骤2：拆分文本为「英文单词片段」和「非英文片段（中文+符号）」，分别处理
        String[] contentSegments = WHITESPACE_PATTERN.split(simplifiedContent);
        for (String segment : contentSegments) {
            if (segment.isEmpty()) {
                continue;
            }

            // 标记当前片段是否为英文单词（包含英文字母即为英文片段）
            boolean isEnglishSegment = ENGLISH_LETTER_PATTERN.matcher(segment).find();
            if (isEnglishSegment) {
                // 英文片段：提取单词首字符
                char firstEnglishChar = ' ';
                for (char c : segment.toCharArray()) {
                    if (ENGLISH_LETTER_PATTERN.matcher(String.valueOf(c)).matches()) {
                        firstEnglishChar = isUppercase ? Character.toUpperCase(c) : Character.toLowerCase(c);
                        break;
                    }
                }
                if (firstEnglishChar != ' ') {
                    resultBuilder.append(firstEnglishChar);
                }
            } else {
                // 中文/符号片段：提取中文拼音首字母
                char[] charArray = segment.toCharArray();
                HanyuPinyinOutputFormat pinyinFormat = isUppercase ? UPPERCASE_PINYIN_FORMAT : LOWERCASE_PINYIN_FORMAT;
                for (char c : charArray) {
                    if (CHINESE_PATTERN.matcher(String.valueOf(c)).matches()) {
                        try {
                            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, pinyinFormat);
                            if (pinyinArray != null && pinyinArray.length > 0) {
                                resultBuilder.append(pinyinArray[0].charAt(0));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return resultBuilder.toString();
    }

}
