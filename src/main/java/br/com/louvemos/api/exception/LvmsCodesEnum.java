package br.com.louvemos.api.exception;

import org.springframework.http.HttpStatus;

public enum LvmsCodesEnum {

    // IMPORTANT: NUMBERS MUST NOT REPEAT (prefix)-YYY
    // chord prefix: 1
    // song prefix: 2
    // album prefix: 3
    
    CHORD_NULL(HttpStatus.BAD_REQUEST, 1001L, "chord.null"),
    CHORD_BASS_STRING_INVALID(HttpStatus.BAD_REQUEST, 1002L, "chord.bass.string.invalid"),
    CHORD_SOUNDED_STRINGS_INVALID(HttpStatus.BAD_REQUEST, 1003L, "chord.sounded.strings.invalid"),
    CHORD_MUTED_STRINGS_INVALID(HttpStatus.BAD_REQUEST, 1004L, "chord.muted.strings.invalid"),
    CHORD_DIAGRAM_INVALID(HttpStatus.BAD_REQUEST, 1005L, "chord.diagram.invalid"),
    CHORD_SYMBOL_INVALID(HttpStatus.BAD_REQUEST, 1006L, "chord.symbol.invalid"),
    
    SONG_NULL(HttpStatus.BAD_REQUEST, 2001L, "song.null"),
    
    ALBUM_NULL(HttpStatus.BAD_REQUEST, 3001L, "album.null"),
    
    ARTIST_NULL(HttpStatus.BAD_REQUEST, 4001L, "artist.null");

    private Long code;
    private String key;
    private HttpStatus httpStatus;

    private LvmsCodesEnum(HttpStatus httpStatus, Long code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.key = message;
    }

    public Long getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReadableText(Object... args) {
        return I18N.getString(this.getKey(), args);
    }
}
