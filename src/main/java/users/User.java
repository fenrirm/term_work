package users;

public class User {
    private String name, surname;
    private String isTeacher;
    private int phoneNumber;
    private String password;
    private String imagePath;


    public User(String name, String surname, String isTeacher, int phoneNumber, String password, String imagePath){
        this.name = name;
        this.surname = surname;
        this.isTeacher = isTeacher;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getIsTeacher() {
        return isTeacher;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getImagePath() {
        return imagePath;
    }
}
