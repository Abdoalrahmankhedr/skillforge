package models;

// Andrew :)

import java.util.List;

public class User implements Model {
    private int id;
    private String password;
    private String email;
    private String name;
    private String role;
    private List<Certificate> certificates;

    /* This constructor is necessary for JSON parsing */
    public User() {}

    public void addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
    }

    /* Getters & Setters */
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public List<Certificate> getCertificates() {return certificates;}
    public void setCertificates(List<Certificate> certificates) {this.certificates = certificates;}
}
