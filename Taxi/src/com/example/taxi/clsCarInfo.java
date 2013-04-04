package com.example.taxi;

public class clsCarInfo {
	private String Car;
	private String CarName;

    
	public clsCarInfo() {
	}
	

	public String getCar() {
		return this.Car;
	}
	public void setCar(String Car) {
		this.Car = Car;
	}
	
	public String getCarName() {
		return this.CarName;
	}
	public void setCarName(String CarName) {
		this.CarName = CarName;
	}

	


public String toString() {
    return "Car Car=" + Car + ", CarName=" + CarName;
}
}
