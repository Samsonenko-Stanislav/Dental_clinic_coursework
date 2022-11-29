package com.clinic.dentistry.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class  User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

    private String email;

    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "outpatient_card_id")
    private OutpatientCard outpatientCard;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public String getFullName() {
        String fullName = "";
        if (employee != null){
            fullName = employee.getFullName();
        } else if (outpatientCard != null) {
            fullName = outpatientCard.getFullName();
        }
        return fullName;
    }
    public void setFullName(String fullName) {
        if (employee != null){
            employee.setFullName(fullName);
        }
        if (outpatientCard != null) {
            outpatientCard.setFullName(fullName);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OutpatientCard getOutpatientCard() {
        return outpatientCard;
    }

    public void setOutpatientCard(OutpatientCard outpatientCard) {
        this.outpatientCard = outpatientCard;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public String getEmail(){
        return  email != null ? email : "None";
    }

    public Gender getGender(){
        return gender != null ? gender : Gender.None;
    }

    public String employeeJobTitle(){
        return employee.getJobTitle();
    }

}
