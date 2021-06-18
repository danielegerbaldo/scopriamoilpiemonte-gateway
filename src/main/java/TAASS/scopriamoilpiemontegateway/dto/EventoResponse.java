package TAASS.scopriamoilpiemontegateway.dto;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventoResponse {
    private Long id;

    private String nome;

    private int numMaxPartecipanti;

    private int partecipanti;

    private boolean streaming;

    private String descrizione;

    private String note;

    private TipoEvento tipoEvento;

    private Date data;

    private Utente proprietario;

    private long comune;

    private String indirizzo;

    private Set<Utente> iscritti;

    private double prezzo;

    private double latitudine;

    private double longitudine;

    public EventoResponse(Evento evento){
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.numMaxPartecipanti = evento.getNumMaxPartecipanti();
        this.partecipanti = evento.getPartecipanti();
        this.streaming = evento.isStreaming();
        this.descrizione = evento.getDescrizione();
        this.note = evento.getNote();
        this.tipoEvento = evento.getTipoEvento();
        this.data = evento.getData();
        this.comune = evento.getComune();   //TODO: modifica
        this.indirizzo = evento.getIndirizzo();
        this.prezzo = evento.getPrezzo();
        this.latitudine = evento.getLatitudine();
        this.longitudine = evento.getLongitudine();
        proprietario = null;
        iscritti = null;
    }

    public static EventoResponse makeEventoResponse(Tuple3<Evento, Utente, List<Utente>> info){
        EventoResponse eventoResponse = new EventoResponse(info.getT1());
        eventoResponse.setProprietario(info.getT2());
        eventoResponse.setIscritti(new HashSet<Utente>(info.getT3()));
        return eventoResponse;
    }

    public EventoResponse() {
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

    public int getNumMaxPartecipanti() {
        return numMaxPartecipanti;
    }

    public void setNumMaxPartecipanti(int numMaxPartecipanti) {
        this.numMaxPartecipanti = numMaxPartecipanti;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public long getComune() {
        return comune;
    }

    public void setComune(long comune) {
        this.comune = comune;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Set<Utente> getIscritti() {
        return iscritti;
    }

    public void setIscritti(Set<Utente> iscritti) {
        this.iscritti = iscritti;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
}
