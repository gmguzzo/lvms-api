package br.com.louvemos.api.exception;

import org.springframework.http.HttpStatus;

public enum LvmsCodesEnum {

    // IMPORTANT: NUMBERS MUST NOT REPEAT (prefix)-YYY
    // chord prefix: 1
    // song prefix: 2
    // album prefix: 3
    // artist prefix: 4
    // category prefix: 5
    // person prefix: 6
    // role prefix: 7
    
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, 0001L, "forbidden"),
    GENERIC_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 0002L, "generic.internal.server.error"),
    JSON_INVALID_FORMAT(HttpStatus.INTERNAL_SERVER_ERROR, 0003L, "json.invalid.format"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 0004L, "forbidden"),
    
    CHORD_NULL(HttpStatus.BAD_REQUEST, 1001L, "chord.null"),
    CHORD_BASS_STRING_INVALID(HttpStatus.BAD_REQUEST, 1002L, "chord.bass.string.invalid"),
    CHORD_SOUNDED_STRINGS_INVALID(HttpStatus.BAD_REQUEST, 1003L, "chord.sounded.strings.invalid"),
    CHORD_MUTED_STRINGS_INVALID(HttpStatus.BAD_REQUEST, 1004L, "chord.muted.strings.invalid"),
    CHORD_DIAGRAM_INVALID(HttpStatus.BAD_REQUEST, 1005L, "chord.diagram.invalid"),
    CHORD_SYMBOL_INVALID(HttpStatus.BAD_REQUEST, 1006L, "chord.symbol.invalid"),
    
    SONG_NULL(HttpStatus.BAD_REQUEST, 2001L, "song.null"),
    SONG_TONE_INVALID(HttpStatus.BAD_REQUEST, 2002L, "song.tone.invalid"),
    SONG_STATUS_INVALID(HttpStatus.BAD_REQUEST, 2003L, "song.status.invalid"), 
    SONG_LYRIC_INVALID(HttpStatus.BAD_REQUEST, 2004L, "song.lyric.invalid"), 
    SONG_DESCRIPTION_INVALID(HttpStatus.BAD_REQUEST, 2005L, "song.description.invalid"), 
    SONG_TITLE_INVALID(HttpStatus.BAD_REQUEST, 2006L, "song.title.invalid"), 
    
    ALBUM_NULL(HttpStatus.BAD_REQUEST, 3001L, "album.null"),
    ALBUM_NAME_INVALID(HttpStatus.BAD_REQUEST, 3002L, "album.name.invalid"),
    ALBUM_GENRE_INVALID(HttpStatus.BAD_REQUEST, 3003L, "album.genre.invalid"),
    ALBUM_DEBUT_INVALID(HttpStatus.BAD_REQUEST, 3004L, "album.debut.invalid"),
    
    ARTIST_NULL(HttpStatus.BAD_REQUEST, 4001L, "artist.null"),
    ARTIST_NAME_INVALID(HttpStatus.BAD_REQUEST, 4002L, "artist.name_.invalid"),
    ARTIST_GENRE_INVALID(HttpStatus.BAD_REQUEST, 4003L, "artist.genre.invalid"),
    ARTIST_SINCE_INVALID(HttpStatus.BAD_REQUEST, 4004L, "artist.since.invalid"),
    
    CATEGORY_NULL(HttpStatus.BAD_REQUEST, 5001L, "category.null"),
    CATEGORY_NAME_INVALID(HttpStatus.BAD_REQUEST, 5002L, "category.name.invalid"),
    CATEGORY_DESCRIPTION_INVALID(HttpStatus.BAD_REQUEST, 5003L, "category.description.invalid"),
    
    PERSON_NULL(HttpStatus.BAD_REQUEST, 6001L, "person.null"),
    PERSON_USERNAME_INVALID(HttpStatus.BAD_REQUEST, 6002L, "person.username.invalid"),
    PERSON_PASSWORD_INVALID(HttpStatus.BAD_REQUEST, 6003L, "person.password.invalid"),
    
    ROLE_NULL(HttpStatus.BAD_REQUEST, 7001L, "role.null");

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
