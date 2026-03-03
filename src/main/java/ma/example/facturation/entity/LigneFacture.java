package ma.example.facturation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "lignes_facture")
@Data
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private BigDecimal prixUnitaireHt;

    @Column(nullable = false)
    private BigDecimal tauxTva;

    private BigDecimal totalHt;
    private BigDecimal totalTva;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Facture facture;
}