package by.lukyanov.finaltask.entity;

import java.math.BigDecimal;

public final class User extends AbstractEntity{
    private String email;
    private String password;
    private String name;
    private String surname;
    private UserRole role;
    private UserStatus status;
    private String phone;
    private BigDecimal balance;
    private String driverLicense;

    public User(){
        super();
    }

    public User(long id) {
        super(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (role != user.role) return false;
        if (status != user.status) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        return driverLicense != null ? driverLicense.equals(user.driverLicense) : user.driverLicense == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (driverLicense != null ? driverLicense.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }

    public static class UserBuilder{
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder id(long id){
            user.setId(id);
            return this;
        }

        public UserBuilder name(String name){
            user.name = name;
            return this;
        }

        public UserBuilder surname(String surname){
            user.surname = surname;
            return this;
        }

        public UserBuilder email(String email){
            user.email = email;
            return this;
        }

        public UserBuilder password(String password){
            user.password = password;
            return this;
        }

        public UserBuilder role(UserRole role){
            user.role = role;
            return this;
        }

        public UserBuilder status(UserStatus status){
            user.status = status;
            return this;
        }

        public UserBuilder phone(String phone){
            user.phone = phone;
            return this;
        }

        public UserBuilder balance(BigDecimal balance){
            user.balance = balance;
            return this;
        }

        public UserBuilder driverLicense(String driverLicense){
            user.driverLicense = driverLicense;
            return this;
        }

        public User build(){
            return user;
        }
    }
}
