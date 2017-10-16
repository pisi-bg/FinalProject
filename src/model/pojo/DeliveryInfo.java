package model.pojo;

public class DeliveryInfo {

	private long deliveryInfoId;
	private String address;
	private int zipCode;
	private String city;
	private String recieverFirstName;
	private String recieverLastName;
	private String recieverPhone;
	private String notes;

	// constructor to send info in DB
	public DeliveryInfo(String address, int zipCode, String city, String recieverFirstName, String recieverLastName,
			String recieverPhone, String notes) {
		super();
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.recieverFirstName = recieverFirstName;
		this.recieverLastName = recieverLastName;
		this.recieverPhone = recieverPhone;
		this.notes = notes;
	}

	// constructor to retrieve info from DB
	public DeliveryInfo(long deliveryInfoId, String address, int zipCode, String city, String recieverFirstName,
			String recieverLastName, String recieverPhone, String notes) {
		super();
		this.deliveryInfoId = deliveryInfoId;
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.recieverFirstName = recieverFirstName;
		this.recieverLastName = recieverLastName;
		this.recieverPhone = recieverPhone;
		this.notes = notes;
	}

	public void setDeliveryInfoId(long deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}

}
