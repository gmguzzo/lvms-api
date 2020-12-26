package br.com.louvemos.api.chord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class ChordServiceValidator {

    public void validatePersist(Chord c) throws LvmsException {
        validateNull(c);
        validateSymbol(c);
        validateBassString(c);
        validateSoundedStrings(c);
        validateMutedStrings(c);
        validateDiagram(c);
    }

    public void validateChordFound(Chord c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CHORD_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Chord c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CHORD_NULL);
        }
    }

    private void validateBassString(Chord c) throws LvmsException {
        if (c.getBassString() != null && c.getBassString() < 0L) {
            throw new LvmsException(LvmsCodesEnum.CHORD_BASS_STRING_INVALID);
        }
    }

    private void validateSoundedStrings(Chord c) throws LvmsException {
        if (c.getSoundedStrings() != null && c.getSoundedStrings().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_SOUNDED_STRINGS_INVALID);
        }
    }

    private void validateMutedStrings(Chord c) throws LvmsException {
        if (c.getMutedStrings() != null && c.getMutedStrings().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_MUTED_STRINGS_INVALID);
        }
    }

    private void validateDiagram(Chord c) throws LvmsException {
        if (c.getDiagram() != null && c.getDiagram().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_DIAGRAM_INVALID);
        }
    }

    private void validateSymbol(Chord c) throws LvmsException {
        if (StringUtils.isBlank(c.getSymbol())) {
            throw new LvmsException(LvmsCodesEnum.CHORD_SYMBOL_INVALID);
        }
    }

}
