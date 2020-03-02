package given;

import java.util.Objects;

public class ContactInfo {
  
  // First and last name of the contact
  String name;

  // Phone number of the contact, yes it is a string
  String number;
  
  // Address of the contact
  String address;

  // E-mail of the contact
  String email;
  
  public ContactInfo(String name, String number,int preference) {
    setName(name);
    setNumber(number);
  }

  public ContactInfo(String name, String number) {
    this(name, number, 50);
  }

  public String getNumber() {
    return number;
  }


  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
  
  public String getAddress() {
    return address;
  }

  public void setNumber(String num) {
    number = num;
  }

  public void setName(String na) {
    name= na;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public String toString() {
    return name + " " + number;
  }
  
  public String toDeepString() {
    StringBuilder sb = new StringBuilder(1000);
    sb.append("Name: " + name + System.lineSeparator());
    sb.append("Number: " + number + System.lineSeparator());
    if(address != null)
      if(!address.isEmpty())
        sb.append("Address: " + address + System.lineSeparator());
    if(email != null)
      if(!email.isEmpty())
        sb.append("E-mail: " + email  + System.lineSeparator());
    
    
    return sb.toString();
  }

  @Override
  // May change
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!ContactInfo.class.isAssignableFrom(obj.getClass()))
      return false;
    final ContactInfo other = (ContactInfo) obj;
    return (this.name.equals(other.name) && this.number.equals(other.number));
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, number);
  }

}
