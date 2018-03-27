package com.revature.util;

public enum StringUtil {
	    INDIA("India"),
	    RUSSIA("Russia"),
	    NORTH_AMERICA("North America");

	    private String displayName;

	    StringUtil(String displayName) {
	        this.displayName = displayName;
	    }

	    public String displayName() { return displayName; }

	    
	    @Override
	    public String toString() { return displayName; }
	}

