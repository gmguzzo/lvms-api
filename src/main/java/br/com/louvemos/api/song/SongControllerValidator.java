package br.com.louvemos.api.song;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SongControllerValidator {

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        SongDTO s = bdIn.getSong();

        validateSong(s);
        validateAlbum(s);
        validateTitle(s);
        validateDescription(s);
        validateLyric(s);
        validateStatus(s);
        validateTone(s);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        SongDTO sd = bdIn.getSong();

        validateSong(sd);
        validateTitle(sd);
        validateDescription(sd);
        validateLyric(sd);
        validateStatus(sd);
        validateTone(sd);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validateSong(SongDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateTone(SongDTO s) throws LvmsException {
        if (StringUtils.isBlank(s.getTone())) {
            throw new LvmsException(LvmsCodesEnum.SONG_TONE_INVALID);
        }
    }

    private void validateStatus(SongDTO s) throws LvmsException {
        if (StringUtils.isBlank(s.getStatus()) || (!s.getStatus().equals("ACTIVE") && !s.getStatus().equals("DISABLED"))) {
            throw new LvmsException(LvmsCodesEnum.SONG_STATUS_INVALID);
        }
    }

    private void validateLyric(SongDTO s) throws LvmsException {
        if (StringUtils.isBlank(s.getLyric())) {
            throw new LvmsException(LvmsCodesEnum.SONG_LYRIC_INVALID);
        }
    }

    private void validateDescription(SongDTO s) throws LvmsException {
        if (s.getDescription() != null && StringUtils.isBlank(s.getDescription())) {
            throw new LvmsException(LvmsCodesEnum.SONG_DESCRIPTION_INVALID);
        }
    }

    private void validateTitle(SongDTO s) throws LvmsException {
        if (StringUtils.isBlank(s.getTitle())) {
            throw new LvmsException(LvmsCodesEnum.SONG_TITLE_INVALID);
        }
    }

    private void validateAlbum(SongDTO s) throws LvmsException {
        if (s.getAlbum() == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

}
