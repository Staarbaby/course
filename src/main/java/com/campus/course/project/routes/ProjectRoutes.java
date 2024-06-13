package com.campus.course.project.routes;

import com.campus.course.base.routes.BaseRoutes;

public class ProjectRoutes {
    private final static String ROOT = BaseRoutes.API + "/project";
    public final static String CREATE = ROOT;
    public final static String BY_ID = ROOT + "/{id}";
    public final static String EDIT = BY_ID;
    public final static String SEARCH = ROOT;
}
