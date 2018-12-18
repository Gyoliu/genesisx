package com.x.dao.entity;

import java.util.List;

public class SystemMenu {
    private Integer id;

    private String name;

    private String path;

    private Boolean hideInMenu;

    private Boolean hideInBread;

    private Boolean notCache;

    private String title;

    private String icon;

    private String component;

    private Integer parent;

    private String redirect;

    private String href;

    private Integer level;

    private Integer order;

    private String beforeCloseName;

    private String access;

    private Integer userId;

    private List<SystemMenu> childrenMenus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<SystemMenu> getChildrenMenus() {
        return childrenMenus;
    }

    public void setChildrenMenus(List<SystemMenu> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Boolean getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public Boolean getHideInBread() {
        return hideInBread;
    }

    public void setHideInBread(Boolean hideInBread) {
        this.hideInBread = hideInBread;
    }

    public Boolean getNotCache() {
        return notCache;
    }

    public void setNotCache(Boolean notCache) {
        this.notCache = notCache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect == null ? null : redirect.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getBeforeCloseName() {
        return beforeCloseName;
    }

    public void setBeforeCloseName(String beforeCloseName) {
        this.beforeCloseName = beforeCloseName == null ? null : beforeCloseName.trim();
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access == null ? null : access.trim();
    }
}