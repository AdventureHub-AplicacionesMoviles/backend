package com.app.adventurehub.user.enums;

public enum Role {
    TRAVELER(1), AGENCY(2), ADMIN(3);

    private int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
