package it.academy.service.repositories.impl;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.academy.service.utils.Constants.LIKE_QUERY_PATTERN;
import static it.academy.service.utils.Constants.SPACE;

@UtilityClass
public class QueryHelper {

    public static List<String> getSearchKeywords(String keyword) {
        return Arrays.stream(keyword.trim().split(SPACE))
                .map(s -> String.format(LIKE_QUERY_PATTERN, s))
                .collect(Collectors.toList());
    }
}
