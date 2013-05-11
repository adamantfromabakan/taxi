package com.example.taxi;

public class clsOrders {
	private String Order;
	private String Id;
	private String Status;
	private String Ord_date;
	private String Ord_date_beg;
	private String Ord_date_out;
	private String Ord_date_end;
	private String Ord_km;
	private String Price;
	private String Ord_from;
	private String Ord_to;
    
	public clsOrders() {
	}
	

	public String getOrder() {
		return this.Order;
	}
	public void setOrder(String Order) {
		this.Order = Order;
	}
	
	public String getId() {
		return this.Id;
	}
	public void setId(String id) {
		this.Id = id;
	}

	public String getStatus() {
		return this.Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}

	public String getOrd_date() {
		return this.Ord_date;
	}
	public void setOrd_date(String Ord_date) {
		this.Ord_date = Ord_date;
	}

	public String getOrd_date_beg() {
		return this.Ord_date_beg;
	}
	public void setOrd_date_beg(String Ord_date_beg) {
		this.Ord_date_beg = Ord_date_beg;
	}

	public String getOrd_date_out() {
		return this.Ord_date_out;
	}
	public void setOrd_date_out(String Ord_date_out) {
		this.Ord_date_out = Ord_date_out;
	}

	public String getOrd_date_end() {
		return this.Ord_date_end;
	}
	public void setOrd_date_end(String Ord_date_end) {
		this.Ord_date_end = Ord_date_end;
	}

	public String getOrd_km() {
		return this.Ord_km;
	}
	public void setOrd_km(String Ord_km) {
		this.Ord_km = Ord_km;
	}	
	
	public String getPrice() {
		return this.Price;
	}
	public void setPrice(String Price) {
		this.Price = Price;
	}

	public String getOrd_from() {
		return this.Ord_from;
	}
	public void setOrd_from(String Ord_from) {
		this.Ord_from = Ord_from;
	}

	public String getOrd_to() {
		return this.Ord_to;
	}
	public void setOrd_to(String Ord_to) {
		this.Ord_to = Ord_to;
	}


public String toString() {
    return "order Id=" + Id + ", Status=" + Status + ", Ord_date=" + Ord_date + ", Ord_date_beg=" + Ord_date_beg + ", Ord_date_out=" + Ord_date_out
    		+ ", Ord_date_end=" + Ord_date_end + ", Price=" + Price + ", Ord_from=" + Ord_from + ", Ord_to=" + Ord_to ;
}
}
