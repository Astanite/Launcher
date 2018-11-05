package com.astanite.launcher;

class ContactInfo {
    String name;
    String phone;

    ContactInfo(String n, String ph) {
        name = n;
        phone = ph;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}
