package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Partits {
    @Id
    @Column(name = "id_partit")
    private int idPartit;
    @Basic
    @Column(name = "nom_partit")
    private String nomPartit;
    @Basic
    @Column(name = "lloc_partit")
    private String llocPartit;
    @Basic
    @Column(name = "preu")
    private int preu;
    @Basic
    @Column(name = "hora")
    private Time hora;
    @Basic
    @Column(name = "data_partit")
    private Date dataPartit;
    @OneToMany(mappedBy = "partitsByIdPartit")
    private Collection<Tickets> ticketsByIdPartit;

    public int getIdPartit() {
        return idPartit;
    }

    public void setIdPartit(int idPartit) {
        this.idPartit = idPartit;
    }

    public String getNomPartit() {
        return nomPartit;
    }

    public void setNomPartit(String nomPartit) {
        this.nomPartit = nomPartit;
    }

    public String getLlocPartit() {
        return llocPartit;
    }

    public void setLlocPartit(String llocPartit) {
        this.llocPartit = llocPartit;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getDataPartit() {
        return dataPartit;
    }

    public void setDataPartit(Date dataPartit) {
        this.dataPartit = dataPartit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partits partits = (Partits) o;

        if (idPartit != partits.idPartit) return false;
        if (nomPartit != null ? !nomPartit.equals(partits.nomPartit) : partits.nomPartit != null) return false;
        if (llocPartit != null ? !llocPartit.equals(partits.llocPartit) : partits.llocPartit != null) return false;
        if (hora != null ? !hora.equals(partits.hora) : partits.hora != null) return false;
        if (dataPartit != null ? !dataPartit.equals(partits.dataPartit) : partits.dataPartit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPartit;
        result = 31 * result + (nomPartit != null ? nomPartit.hashCode() : 0);
        result = 31 * result + (llocPartit != null ? llocPartit.hashCode() : 0);
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        result = 31 * result + (dataPartit != null ? dataPartit.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsByIdPartit() {
        return ticketsByIdPartit;
    }

    public void setTicketsByIdPartit(Collection<Tickets> ticketsByIdPartit) {
        this.ticketsByIdPartit = ticketsByIdPartit;
    }
}
