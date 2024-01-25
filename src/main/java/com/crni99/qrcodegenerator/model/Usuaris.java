package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Usuaris {
    @Id
    @Column(name = "DNI")
    private String dni;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "usuari")
    private String usuari;
    @Basic
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "usuarisByDni")
    private Collection<Tickets> ticketsByDni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuaris usuaris = (Usuaris) o;

        if (dni != null ? !dni.equals(usuaris.dni) : usuaris.dni != null) return false;
        if (email != null ? !email.equals(usuaris.email) : usuaris.email != null) return false;
        if (usuari != null ? !usuari.equals(usuaris.usuari) : usuaris.usuari != null) return false;
        if (password != null ? !password.equals(usuaris.password) : usuaris.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dni != null ? dni.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (usuari != null ? usuari.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsByDni() {
        return ticketsByDni;
    }

    public void setTicketsByDni(Collection<Tickets> ticketsByDni) {
        this.ticketsByDni = ticketsByDni;
    }
}
