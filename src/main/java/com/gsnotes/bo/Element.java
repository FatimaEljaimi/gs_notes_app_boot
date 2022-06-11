package com.gsnotes.bo;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.YearMonth;
import java.util.List;


/**
 * Represente un élément d'un module
 *
 * @author T. BOUDAA
 */

@Entity
@PrimaryKeyJoinColumn(name = "idElement")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatiere;

    private String nom;

    private String code;

    @Min(value = 0)
    @Max(value = 1)
    private double currentCoefficient;

    @ManyToOne
    @JoinColumn(name = "idModule")
    private Module module;


    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, targetEntity = elementEnseignants.class)
    private List<elementEnseignants> Enseignants;

    public Long getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Long idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public double getCurrentCoefficient() {
        return currentCoefficient;
    }

    public void setCurrentCoefficient(double currentCoefficient) {
        this.currentCoefficient = currentCoefficient;
    }


    public List<elementEnseignants> getEnseignants() {
        return Enseignants;
    }

    public void setEnseignants(List<elementEnseignants> enseignants) {
        Enseignants = enseignants;
    }


    public Enseignant getCurrentEnseignant() {
        int year = (YearMonth.now().getMonthValue() >= 10) ? YearMonth.now().getYear() + 1 : YearMonth.now().getYear();

        for (elementEnseignants enseignant : Enseignants) {
            if (enseignant.getAnnee() == year) {
                return enseignant.getEnseignant();
            }
        }
        return null;
    }
}