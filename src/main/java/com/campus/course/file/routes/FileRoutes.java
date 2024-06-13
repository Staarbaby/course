package com.campus.course.file.routes;

import com.campus.course.base.routes.BaseRoutes;

public class FileRoutes {
    private final static String ROOT = BaseRoutes.API + "/file";
    public final static String CREATE = ROOT;
    public final static String BY_ID = ROOT + "/{id}";
    public final static String EDIT = BY_ID;
    public final static String SEARCH = ROOT;
}
