package com.example.libraryapplication.helper;

public class SortPriority {
    public static int getPriorityPM(String trangThai) {
        switch (trangThai) {
            case "Chưa trả":
                return 0;
            case "Vi phạm":
                return 1;
            case "Đã trả":
                return 2;
            default:
                return 3;
        }
    }
}
