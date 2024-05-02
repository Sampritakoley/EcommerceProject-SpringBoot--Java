package com.ecommerce.ecommerce.Dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterDataDto {

    @NotBlank(message = "First Name should not be blank")
    @Size(min=3, max=10, message = "Size should be greater than 3 and less than 10")
    private String first_name;

    @NotBlank(message = "Last Name should not be blank")
    @Size(min=3, max=10, message = "Size should be greater than 3 and less than 10")
    private String last_name;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Pattern(regexp = "^(.+)@(.+)$" , message ="enter a valid email address")
    private String email;

    private String role;

    private String image;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",message = "At least 8 chars " +
            "\n" +
            "| Contains at least one digit \n" +
            "\n" +
            "| Contains at least one lower alpha char and one upper alpha char \n" +
            "\n" +
            "| Contains at least one char within a set of special chars (@#%$^ etc.) \n" +
            "\n" +
            "| Does not contain space, tab, etc.")
    private String password;

    @AssertTrue(message = "Make sure you have agreed terms & conditions")
    private boolean agreed;

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }
}
