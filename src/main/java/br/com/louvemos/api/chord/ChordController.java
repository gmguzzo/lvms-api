/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.chord;

import br.com.louvemos.api.base.BaseController;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.ControllerUtils;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author gmguzzo
 */
@Controller
@RequestMapping(value = "/v2/chords")
public class ChordController extends BaseController {

    @Autowired
    private ChordService chordService;

    @Autowired
    private ChordControllerValidator chordControllerValidator;

    @Autowired
    private ChordConverter chordConverter;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "symbols") String symbols,
            @RequestParam(required = false, value = "qSymbol") String qSymbol,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

        chordControllerValidator.validateList(ids, symbols, qSymbol, firstResult, maxResults, sort);

        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);
        List<Long> cIdList = ControllerUtils.parseCSVToLongList(ids);

        List<String> symbolList = ControllerUtils.parseCSVToStringList(symbols);

        List<Chord> cList = chordService.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortMap);

        BaseDTO bd = new BaseDTO();
        List<ChordDTO> cdList = new ArrayList<>();
        cList.forEach((c) -> {
            cdList.add(chordConverter.toDTO(c));
        });

        bd.setChords(cdList);

        return bd;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        chordControllerValidator.validateCreate(bdIn);

        // Convert
        Chord c = chordConverter.toModel(null, bdIn.getChord());

        // Create
        Chord cPersist = chordService.create(c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setChord(chordConverter.toDTO(cPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO update(
            @PathVariable(value = "id") Long id,
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        chordControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Chord c = chordConverter.toModel(id, bdIn.getChord());

        // Update
        Chord cPersist = chordService.update(c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setChord(chordConverter.toDTO(cPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        chordControllerValidator.validateDelete(id);

        //Delete
        chordService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

    @RequestMapping(value = "raiseHalfTone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO raiseHalfTone(
            @RequestParam(required = false, value = "chord") String chord
    ) throws Exception {
        String nextChord;

        if (chord == null || NoteProgressionEnum.valueOf(chord) == null) {
            return null;
        }

        NoteProgressionEnum note = NoteProgressionEnum.valueOf(chord);

        if (note.isHasSharp()) {
            nextChord = NoteProgressionEnum.getSharpRepresentation(note);
        } else {
            NoteProgressionEnum nextNote = NoteProgressionEnum.getNextNote(note);
            nextChord = nextNote.toString();
        }

        BaseDTO bd = new BaseDTO();
        bd.setNextChord(nextChord);

        return bd;
    }

}
