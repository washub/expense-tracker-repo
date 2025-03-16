package com.expense.ds_service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    private static final  Logger log = LoggerFactory.getLogger(MessageUtils.class);

    private static final List<String> bagOfWord = List.of(
            "spent","card","bank","amount","debited", "purchase"
    );

    public static boolean isBankMessage(String message){
        // Build the regex pattern by joining the words with '|'
        // The pattern uses:
        //   - (?i) for case-insensitivity,
        //   - \\b for word boundaries,
        //   - (?: ...) for a non-capturing group.
        String regex = "(?i)\\b(" + String.join("|", bagOfWord) + ")\\b";
//        log.info("Generated regex: {}",  regex);

        // Create a Pattern object from the regex
        Pattern pattern = Pattern.compile(regex);

        // Example message to test
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }
}
