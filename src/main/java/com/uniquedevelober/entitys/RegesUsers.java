/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.entitys;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mahjouba
 */
@Entity
@Table(name = "reges_users", catalog = "hellbase2", schema = "")
@NamedQueries({
    @NamedQuery(name = "RegesUsers.findAll", query = "SELECT r FROM RegesUsers r"),
    @NamedQuery(name = "RegesUsers.findById", query = "SELECT r FROM RegesUsers r WHERE r.id = :id"),
    @NamedQuery(name = "RegesUsers.findByEmail", query = "SELECT r FROM RegesUsers r WHERE r.email = :email"),
    @NamedQuery(name = "RegesUsers.findByPassword", query = "SELECT r FROM RegesUsers r WHERE r.password = :password"),
    @NamedQuery(name = "RegesUsers.findByUsername", query = "SELECT r FROM RegesUsers r WHERE r.username = :username")})
public class RegesUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String email;
    @Size(max = 255)
    @Column(length = 255)
    private String password;
    @Size(max = 255)
    @Column(length = 255)
    private String username;
    @JoinColumn(name = "owner_user_id", referencedColumnName = "userid")
    @ManyToOne
    private Users ownerUserId;

    public RegesUsers() {
    }

    public RegesUsers(Long id) {
        this.id = id;
    }

    public RegesUsers(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Users ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegesUsers)) {
            return false;
        }
        RegesUsers other = (RegesUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uniquedevelober.RegesUsers[ id=" + id + " ]";
    }
    
}
