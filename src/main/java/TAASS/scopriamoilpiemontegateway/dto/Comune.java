package TAASS.scopriamoilpiemontegateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Comune {
    public Comune() {
    }

    public Comune(Long istat, String nome, String CAP, String provincia) {
        this.istat = istat;
        this.nome = nome;
        this.CAP = CAP;
        this.provincia = provincia;
    }

    private Long istat;
    private String nome;
    private String CAP;
    private String provincia;

    public Long getIstat() {
        return istat;
    }

    public void setIstat(Long id) {
        this.istat = istat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
