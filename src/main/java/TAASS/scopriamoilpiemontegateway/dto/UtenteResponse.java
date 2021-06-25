package TAASS.scopriamoilpiemontegateway.dto;

import reactor.util.function.Tuple3;
import java.util.List;
import java.util.Optional;

public class UtenteResponse {
    private Long id;
    private String nome;
    private String cognome;
    private String cf;
    private String telefono;
    private Comune comuneResidenza;   //comune di residenza
    private String email;
    private String password;
    List<Role> ruoli;
    private Comune dipendenteDiComune;

    public UtenteResponse (Utente utente){
        this.id = utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.cf = utente.getCf();
        this.telefono = utente.getTelefono();
        this.email = utente.getEmail();
        //this.password = utente.getPassword();
        this.ruoli = utente.getRuoli();
        this.comuneResidenza = null;
        this.dipendenteDiComune = null;
    }

    public static UtenteResponse makeUtenteResponse(Tuple3<Utente, Optional<Comune>, Optional<Comune>> info){

        UtenteResponse utenteResponse = new UtenteResponse(info.getT1());

        if (info.getT2().isPresent()) {
            utenteResponse.setComuneResidenza(info.getT2().get());
        } else {
            utenteResponse.setComuneResidenza(null);
        }

        if (info.getT3().isPresent()) {
            utenteResponse.setDipendenteDiComune(info.getT3().get());
        } else {
            utenteResponse.setDipendenteDiComune(null);
        }


        return utenteResponse;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Comune getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(Comune comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
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

    public List<Role> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<Role> ruoli) {
        this.ruoli = ruoli;
    }

    public Comune getDipendenteDiComune() {
        return dipendenteDiComune;
    }

    public void setDipendenteDiComune(Comune dipendenteDiComune) {
        this.dipendenteDiComune = dipendenteDiComune;
    }
}
