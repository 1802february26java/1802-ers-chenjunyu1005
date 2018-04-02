package com.revature.util;

public enum StringUtil {
	    MANAGER("MANAGER"),
	    EMPLOYEE("EMPLOYEE"),
	    UPDATESUCCESSFULLY("UPDATE SUCCESSFULLY");

	    private String displayName;

	    StringUtil(String displayName) {
	        this.displayName = displayName;
	    }

	    public String displayName() { return displayName; }

	    
	    @Override
	    public String toString() { return displayName; }
	}

