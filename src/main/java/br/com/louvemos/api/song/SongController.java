/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.song;

import br.com.louvemos.api.category.Category;
import br.com.louvemos.api.category.CategoryDTO;
import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.album.AlbumConverter;
import br.com.louvemos.api.album.AlbumDTO;
import br.com.louvemos.api.artist.ArtistConverter;
import br.com.louvemos.api.artist.ArtistDTO;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.ControllerUtils;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.category.CategoryConverter;
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
@RequestMapping(value = "/v2/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongControllerValidator songControllerValidator;

    @Autowired
    private SongConverter songConverter;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private ArtistConverter artistConverter;

    @Autowired
    private CategoryConverter categoryConverter;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "albumIds") String albumIds,
            @RequestParam(required = false, value = "artistIds") String artistIds,
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "categories") String categories,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

//        songControllerValidator.validateList(ids, symbols, qSymbol, firstResult, maxResults, sort);
        List<Long> idList = ControllerUtils.parseCSVToLongList(ids);
        List<Long> albumIdList = ControllerUtils.parseCSVToLongList(albumIds);
        List<Long> artistIdList = ControllerUtils.parseCSVToLongList(artistIds);
        List<String> categoryList = ControllerUtils.parseCSVToStringList(categories);
        
        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);

        List<Song> list = songService.list(idList, albumIdList, artistIdList, q, categoryList, firstResult, maxResults, sortMap);

        List<SongDTO> sdList = new ArrayList<>();
        for (Song s : list) {
            Album a = s.getAlbum();
            AlbumDTO ad = albumConverter.toDTO(a);

            Artist ar = a.getArtist();
            ArtistDTO ard = artistConverter.toDTO(ar);

            ad.setArtist(ard);

            SongDTO sd = songConverter.toDTO(s);
            sd.setAlbum(ad);
            sdList.add(sd);
        }

        BaseDTO bd = new BaseDTO();
        bd.setSongs(sdList);

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
        songControllerValidator.validateCreate(bdIn);

        // Convert
        Song s = songConverter.toModel(null, bdIn.getSong());
        Album a = albumConverter.toModel(bdIn.getSong().getAlbum().getId(), bdIn.getSong().getAlbum());

        Artist ar = null;
        if (bdIn.getSong().getAlbum().getArtist() != null) {
            ar = artistConverter.toModel(bdIn.getSong().getAlbum().getArtist().getId(), bdIn.getSong().getAlbum().getArtist());
        }

        List<Category> cList = new ArrayList<>();
        if (bdIn.getSong().getCategories() != null && !bdIn.getSong().getCategories().isEmpty()) {
            for (CategoryDTO cd : bdIn.getSong().getCategories()) {
                cList.add(categoryConverter.toModel(cd.getId(), cd));
            }
        }

        // Create
        Song sPersist = songService.create(s, a, ar, cList);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSong(songConverter.toDTO(sPersist));

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
        songControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Song s = songConverter.toModel(id, bdIn.getSong());

        // Update
        Song sPersist = songService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSong(songConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        songControllerValidator.validateDelete(id);

        //Delete
        songService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}