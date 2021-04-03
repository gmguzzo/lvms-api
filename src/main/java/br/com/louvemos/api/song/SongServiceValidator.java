package br.com.louvemos.api.song;

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
public class SongServiceValidator {

    public void validatePersist(Song s) throws LvmsException {
        validateNull(s);

        validateAlbum(s);
        validateTitle(s);
        validateDescription(s);
        validateLyric(s);
        validateStatus(s);
        validateTone(s);

        validatePrivateSongHasPerson(s);
    }

    public void validateSongFound(Song c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Song c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateTone(Song s) throws LvmsException {
        if (StringUtils.isBlank(s.getTone())) {
            throw new LvmsException(LvmsCodesEnum.SONG_TONE_INVALID);
        }
    }

    private void validateStatus(Song s) throws LvmsException {
        if (StringUtils.isBlank(s.getStatus()) || (!s.getStatus().equals("ACTIVE") && !s.getStatus().equals("DISABLED"))) {
            throw new LvmsException(LvmsCodesEnum.SONG_STATUS_INVALID);
        }
    }

    private void validateLyric(Song s) throws LvmsException {
        if (StringUtils.isBlank(s.getLyric())) {
            throw new LvmsException(LvmsCodesEnum.SONG_LYRIC_INVALID);
        }
    }

    private void validateDescription(Song s) throws LvmsException {
        if (s.getDescription() != null && StringUtils.isBlank(s.getLyric())) {
            throw new LvmsException(LvmsCodesEnum.SONG_DESCRIPTION_INVALID);
        }
    }

    private void validateTitle(Song s) throws LvmsException {
        if (StringUtils.isBlank(s.getTitle())) {
            throw new LvmsException(LvmsCodesEnum.SONG_TITLE_INVALID);
        }
    }

    private void validateAlbum(Song s) throws LvmsException {
        if (s.getAlbum() == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    private void validatePrivateSongHasPerson(Song s) throws LvmsException {
        if (s.getPerson() == null && !s.isPublic()) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

}
