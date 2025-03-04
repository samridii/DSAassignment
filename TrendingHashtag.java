//Question 4a

import java.util.*;
import java.util.regex.*;

class TrendingHash {
    // Fields to store tweets and their dates
    private List<String> tweets;
    private List<String> tweetDates;

    // Constructor to initialize tweets and dates
    public TrendingHash(List<String> tweets, List<String> tweetDates) {
        this.tweets = tweets;
        this.tweetDates = tweetDates;
    }

    // Method to find the top 3 trending hashtags
    public List<Map.Entry<String, Integer>> findTopTrendingHashtags() {
        // Map to store hashtag counts
        Map<String, Integer> hashtagCounts = new HashMap<>();

        // Regex to find hashtags in a tweet
        Pattern pattern = Pattern.compile("#\\w+");

        // Iterate through tweets and dates
        for (int i = 0; i < tweets.size(); i++) {
            String tweet = tweets.get(i);
            String tweetDate = tweetDates.get(i);

            // Check if the tweet is from February 2024
            if (tweetDate.startsWith("2024-02")) {
                // Find all hashtags in the tweet
                Matcher matcher = pattern.matcher(tweet);
                while (matcher.find()) {
                    String hashtag = matcher.group();
                    // Update the count for the hashtag
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Convert the map to a list of entries
        List<Map.Entry<String, Integer>> hashtagList = new ArrayList<>(hashtagCounts.entrySet());

        // Sort the list by count descending and then by hashtag descending
        hashtagList.sort((a, b) -> {
            int countCompare = b.getValue().compareTo(a.getValue());
            if (countCompare != 0) {
                return countCompare;
            } else {
                return b.getKey().compareTo(a.getKey());
            }
        });

        // Return the top 3 hashtags
        return hashtagList.subList(0, Math.min(3, hashtagList.size()));
    }

    // Method to print the top hashtags in a table format
    public void printTopHashtags(List<Map.Entry<String, Integer>> topHashtags) {
        System.out.println("+-----------+-------+");
        System.out.println("| Hashtag   | Count |");
        System.out.println("+-----------+-------+");
        for (Map.Entry<String, Integer> entry : topHashtags) {
            System.out.printf("| %-10s| %-6d|\n", entry.getKey(), entry.getValue());
        }
        System.out.println("+-----------+-------+");
    }
}

public class TrendingHashtag{ // Class name matches the filename (Main.java)
    public static void main(String[] args) {
        // Example
        List<String> tweets = Arrays.asList(
            "Enjoying a great start to the day. #HugoDay #WorningUtes",
            "Another #HugoDay with good vileel #FesGood",
            "Productivity posted #WorkLife #ProductJobBy",
            "Exploring new tech frontiers. #TechLife #Innovation",
            "Swiftlude for today's memcrts. #HugoDay #Thankful",
            "Innovation drives us. #TechLife #FutureTech",
            "Connecting with nature's severity. #Nature #Pouseful"
        );

        List<String> tweetDates = Arrays.asList(
            "2024-02-01",
            "2024-02-03",
            "2024-02-04",
            "2024-02-05",
            "2024-02-05",
            "2024-02-07",
            "2024-02-11"
        );

        // Create an instance of TrendingHash
        TrendingHash trendingHash = new TrendingHash(tweets, tweetDates);

        // Find the top 3 trending hashtags
        List<Map.Entry<String, Integer>> topHashtags = trendingHash.findTopTrendingHashtags();

        trendingHash.printTopHashtags(topHashtags);
    }
}
