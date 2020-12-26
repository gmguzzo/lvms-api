package br.com.louvemos.api.chord;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ChordControllerValidator {

    void validateList(String ids, String symbols, String qSymbol, Integer firstResult, Integer maxResults, String sort) {
    }

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        ChordDTO c = bdIn.getChord();

        validateChord(c);
        validateSymbol(c);
        validateBassString(c);
        validateSoundedStrings(c);
        validateMutedStrings(c);
        validateDiagram(c);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        ChordDTO c = bdIn.getChord();

        validateChord(c);
        validateSymbol(c);
        validateBassString(c);
        validateSoundedStrings(c);
        validateMutedStrings(c);
        validateDiagram(c);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.CHORD_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.CHORD_NULL);
        }
    }

    private void validateChord(ChordDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CHORD_NULL);
        }
    }

    private void validateBassString(ChordDTO c) throws LvmsException {
        if (c.getBassString() != null && c.getBassString() < 0L) {
            throw new LvmsException(LvmsCodesEnum.CHORD_BASS_STRING_INVALID);
        }
    }

    private void validateSoundedStrings(ChordDTO c) throws LvmsException {
        if (c.getSoundedStrings() != null && c.getSoundedStrings().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_SOUNDED_STRINGS_INVALID);
        }
    }

    private void validateMutedStrings(ChordDTO c) throws LvmsException {
        if (c.getMutedStrings() != null && c.getMutedStrings().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_MUTED_STRINGS_INVALID);
        }
    }

    private void validateDiagram(ChordDTO c) throws LvmsException {
        if (c.getDiagram() != null && c.getDiagram().isEmpty()) {
            throw new LvmsException(LvmsCodesEnum.CHORD_DIAGRAM_INVALID);
        }
    }

    private void validateSymbol(ChordDTO c) throws LvmsException {
        if (StringUtils.isBlank(c.getSymbol())) {
            throw new LvmsException(LvmsCodesEnum.CHORD_SYMBOL_INVALID);
        }
    }

}
