public class User {

    private String fullName;
    private int age;
    private String phoneNumber;
    private String gender;
    private String address;

    public User(String fullName, int age, String phoneNumber, String gender, String address) {
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean hasSameName(String name) {
        return this.fullName.equalsIgnoreCase(name);
    }

    @Override
    public String toString() {
        return String.format("ФИО='%s', Возраст=%d, Телефон='%s', Пол='%s', Адрес='%s'",
                fullName, age, phoneNumber, gender, address);
    }
}
