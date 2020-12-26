package br.com.louvemos.api.chord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChordService {

    @Autowired
    private ChordServiceValidator chordServiceValidator;

    @Autowired
    private ChordRepository chordRepository;

    public Chord update(Chord c) throws LvmsException {
        Chord cPersist = load(c.getId());
        chordServiceValidator.validateChordFound(cPersist);

        cPersist.setSymbol(c.getSymbol());
        cPersist.setBar(c.getBar());
        cPersist.setStartFret(c.getStartFret());
        cPersist.setBassString(c.getBassString());
        cPersist.setSoundedStrings(c.getSoundedStrings());
        cPersist.setMutedStrings(c.getMutedStrings());
        cPersist.setDiagram(c.getDiagram());

        cPersist.setUpTimestamps();
        chordServiceValidator.validatePersist(cPersist);

        return chordRepository.update(cPersist);

    }

    public Chord load(Long id) {
        return chordRepository.loadById(id);
    }

    public List<Chord> list(
            String qSymbol,
            List<Long> cIdList,
            List<String> symbolList,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "c.id",
                SortDirectionEnum.desc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });

        List<Chord> cList = chordRepository.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortWithDbKeys);

        return cList;
    }

    public Chord create(Chord chord) throws LvmsException {
        chord.setUpTimestamps();

        chordServiceValidator.validatePersist(chord);

        return chordRepository.save(chord);
    }

    public void delete(Long cId) throws LvmsException {
        Chord c = chordRepository.loadById(cId);
        chordServiceValidator.validateChordFound(c);

        chordRepository.delete(c);
    }

}
