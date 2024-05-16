package com.sishui.words.util;

import java.util.*;


public class TagBasedTFIDF {
    private Map<String, Integer> tagFrequency; // 标签频率
    private Map<String, Set<String>> userTags; // 用户标签集合
    private int totalUsers; // 总用户数

    public TagBasedTFIDF() {
        tagFrequency = new HashMap<>();
        userTags = new HashMap<>();
        totalUsers = 0;
    }

    // 添加用户及其标签
    public void addUserTags(String userId, Set<String> tags) {
        userTags.putIfAbsent(userId, new HashSet<>());
        userTags.get(userId).addAll(tags);
        totalUsers++;

        // 更新标签频率
        for (String tag : tags) {
            tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
        }
    }

    // 计算 TF-IDF
    public Map<String, Double> calculateTFIDF() {
        Map<String, Double> tfidfScores = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : userTags.entrySet()) {
            String userId = entry.getKey();
            Set<String> tagsForUser = entry.getValue();

            for (String tag : tagsForUser) {
                // 计算 TF (Term Frequency)
                double tf = (double) tagsForUser.size() / userTags.get(userId).size();

                // 计算 IDF (Inverse Document Frequency)
                double idf = Math.log(totalUsers / (double) tagFrequency.get(tag));

                // 计算 TF-IDF
                double tfidf = tf * idf;

                // 累加 TF-IDF 分数，这里简单地将分数加到 tag 上，实际应用中可能需要更复杂的方式
                tfidfScores.put(tag, tfidfScores.getOrDefault(tag, 0.0) + tfidf);
            }
        }

        return tfidfScores;
    }

    public static void main(String[] args) {
        TagBasedTFIDF tagBasedTFIDF = new TagBasedTFIDF();

        // 示例数据，使用 Arrays.asList 创建集合，并传递给 HashSet 构造函数
        tagBasedTFIDF.addUserTags("user1", new HashSet<>(Arrays.asList("tag1", "tag2", "tag3")));
        tagBasedTFIDF.addUserTags("user2", new HashSet<>(Arrays.asList("tag2", "tag3", "tag4")));
        tagBasedTFIDF.addUserTags("user3", new HashSet<>(Arrays.asList("tag1", "tag3", "tag5")));

        // 计算 TF-IDF 分数
        Map<String, Double> tfidfScores = tagBasedTFIDF.calculateTFIDF();

        // 输出结果
        for (Map.Entry<String, Double> entry : tfidfScores.entrySet()) {
            System.out.println("Tag: " + entry.getKey() + ", TF-IDF Score: " + entry.getValue());
        }
    }
}