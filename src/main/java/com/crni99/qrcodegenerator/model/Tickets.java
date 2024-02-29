package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Tickets {
    @Id
    @Column(name = "id_ticket")
    private String idTicket;
    @Basic
    @Column(name = "DNI")
    private String dni;
    @Basic
    @Column(name = "id_partit")
    private Integer idPartit;
    @Basic
    @Column(name = "data_compra")
    private Date dataCompra;
    @Basic
    @Column(name = "dins_camp")
    private Integer dinsCamp;
    @Basic
    @Column(name = "preu")
    private Integer preu;
    @ManyToOne
    @JoinColumn(name = "DNI", referencedColumnName = "DNI", insertable = false, updatable = false)
    private Usuaris usuarisByDni;
    @ManyToOne
    @JoinColumn(name = "id_partit", referencedColumnName = "id_partit", insertable = false, updatable = false)

    private Partits partitsByIdPartit;
    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getIdPartit() {
        return idPartit;
    }

    public void setIdPartit(Integer idPartit) {
        this.idPartit = idPartit;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Integer getDinsCamp() {
        return dinsCamp;
    }

    public void setDinsCamp(Integer dinsCamp) {
        this.dinsCamp = dinsCamp;
    }

    public Integer getPreu() {
        return preu;
    }

    public void setPreu(Integer preu) {
        this.preu = preu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tickets tickets = (Tickets) o;

        if (idTicket != null ? !idTicket.equals(tickets.idTicket) : tickets.idTicket != null) return false;
        if (dni != null ? !dni.equals(tickets.dni) : tickets.dni != null) return false;
        if (idPartit != null ? !idPartit.equals(tickets.idPartit) : tickets.idPartit != null) return false;
        if (dataCompra != null ? !dataCompra.equals(tickets.dataCompra) : tickets.dataCompra != null) return false;
        if (dinsCamp != null ? !dinsCamp.equals(tickets.dinsCamp) : tickets.dinsCamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTicket != null ? idTicket.hashCode() : 0;
        result = 31 * result + (dni != null ? dni.hashCode() : 0);
        result = 31 * result + (idPartit != null ? idPartit.hashCode() : 0);
        result = 31 * result + (dataCompra != null ? dataCompra.hashCode() : 0);
        result = 31 * result + (dinsCamp != null ? dinsCamp.hashCode() : 0);
        return result;
    }

    public Usuaris getUsuarisByDni() {
        return usuarisByDni;
    }

    public void setUsuarisByDni(Usuaris usuarisByDni) {
        this.usuarisByDni = usuarisByDni;
    }

    public Partits getPartitsByIdPartit() {
        return partitsByIdPartit;
    }

    public void setPartitsByIdPartit(Partits partitsByIdPartit) {
        this.partitsByIdPartit = partitsByIdPartit;
    }
}
