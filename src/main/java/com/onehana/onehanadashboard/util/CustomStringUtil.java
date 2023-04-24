package com.onehana.onehanadashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomStringUtil {
    public static boolean isValidDateFormat(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);
        try {
            df.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // 키워드 앞뒤 length만큼 가져오기
    public static List<String> extractKeywordContext(String text, String keyword, int length) {
        List<Integer> indices = new ArrayList<>();

        int keywordLen = keyword.length();
        keyword = keyword.toLowerCase();

        int index = text.toLowerCase().indexOf(keyword);
        while (index >= 0) {
            indices.add(index);
            index = text.toLowerCase().indexOf(keyword, index + keywordLen);
        }

        List<String> result = new ArrayList<>();
        for (int i : indices) {
            int start = Math.max(0, i - length);
            int end = Math.min(text.length(), i + keywordLen + length);
            String context = text.substring(start, end);
            context = context.replaceAll("\n", "");
            result.add(context);
        }
        return result;
    }

    // 맞춤표를 기준으로 나눈 문장 중에 키워드를 포함하는 문장 전체를 출력
    public static List<String> getSentenceContainingKeyword(String text, String keyword) {
        keyword = keyword.toLowerCase();

        List<String> result = new ArrayList<>();
        String[] sentences = text.split("[.]"); // . 단위로 문장 분리

        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyword)) {
                sentence = sentence.replaceAll("\n", "");
                result.add(sentence);
            }
        }
        return result;
    }
}
