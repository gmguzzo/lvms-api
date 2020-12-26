package br.com.louvemos.api.chord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class ChordConverter {

    public Chord toModel(Long id, ChordDTO cd) {
        if (cd == null) {
            if (id == null) {
                return null;
            }
            return new Chord(id);
        }

        Chord c = new Chord();

        c.setId(id);
        c.setBar(cd.getBar());
        c.setStartFret(cd.getStartFret());
        c.setBassString(cd.getBassString());
        c.setSoundedStrings(cd.getSoundedStrings());
        c.setMutedStrings(cd.getMutedStrings());
        c.setDiagram(cd.getDiagram());
        c.setSymbol(cd.getSymbol());

        return c;
    }

    public ChordDTO toDTO(Chord c) {
        if (c == null) {
            return null;
        }

        ChordDTO cd = new ChordDTO();
        cd.setId(c.getId());
        cd.setBar(c.getBar());
        cd.setStartFret(c.getStartFret());
        cd.setBassString(c.getBassString());
        cd.setSoundedStrings(c.getSoundedStrings());
        cd.setMutedStrings(c.getMutedStrings());
        cd.setDiagram(c.getDiagram());
        cd.setSymbol(c.getSymbol());

        return cd;
    }

}
