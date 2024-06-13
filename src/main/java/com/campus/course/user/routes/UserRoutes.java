package com.campus.course.user.routes;

import com.campus.course.base.routes.BaseRoutes;

public class UserRoutes {
    private final static String ROOT = BaseRoutes.API + "/user";
    public final static String REGISTRATION = BaseRoutes.NOT_SECURED + "/api/v1/registration";
    public final static String EDIT = ROOT;
    public final static String BY_ID = ROOT + "/{id}";
    public final static String SEARCH = ROOT;
}
