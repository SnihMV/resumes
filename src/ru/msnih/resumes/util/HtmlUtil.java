package ru.msnih.resumes.util;

import ru.msnih.resumes.model.Organization;

public class HtmlUtil {
    public static String getTimeRange(Organization.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }
}
