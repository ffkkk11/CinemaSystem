package com.mju.util;


import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/19.
 */

public class PageUtil {

    public static int getPage(Map<String, Object> fields) {

        return Optional.ofNullable(fields)
                .map(p -> fields.get("page"))
                .map(p -> p.toString())
                .map(p -> Integer.parseInt(p))
                .orElse(Const.PAGE);

    }

    public static int getPerPage(Map<String, Object> fields) {
        return Optional.ofNullable(fields)
                .map(p -> fields.get("perPage"))
                .map(p -> p.toString())
                .map(p -> Integer.parseInt(p))
                .orElse(Const.PRE_PAGE);
    }

    public static int calculateOffset(int page, int perPage) {
        return calculateOffset(page, perPage, 0);
    }

    public static int calculateOffset(int page, int perPage, int defaultValue) {
        return page < 1 ? defaultValue : (page - 1) * perPage;
    }

    public static int calculateTotalPage(int rowCount, int perPage) {
        return (rowCount % perPage == 0) ? (rowCount / perPage) : (rowCount / perPage + 1);
    }

    public static int parsePage(String pageString, int defaultValue) {
        return parseParameter(pageString, defaultValue);
    }

    public static int parsePerPage(String perPageString, int defaultValue) {
        return parsePage(perPageString, defaultValue);
    }

    private static int parseParameter(String parameterString, int defaultValue) {
        if (parameterString == null) {
            return defaultValue;
        }

        int parameter;
        parameter = Integer.parseInt(parameterString);
        return parameter;
    }

}
