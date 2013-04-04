package com.example.taxi;

public class clsDriverInfo {
	private String Driver;
	private String DriverName;

    
	public clsDriverInfo() {
	}
	

	public String getDriver() {
		return this.Driver;
	}
	public void setDriver(String Driver) {
		this.Driver = Driver;
	}
	
	public String getDriverName() {
		return this.DriverName;
	}
	public void setDriverName(String DriverName) {
		this.DriverName = DriverName;
	}

	


public String toString() {
    return "Driver Driver=" + Driver + ", DriverName=" + DriverName  ;
}
}
