/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.chord;

import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author gmguzzo
 */
@Getter
public enum NoteProgressionEnum {
    C(1, true),
    D(2, true),
    E(3, false),
    F(4, true),
    G(5, true),
    A(6, true),
    B(7, false);

    private final int order;
    private final boolean hasSharp;

    NoteProgressionEnum(int order, boolean hasSharp) {
        this.order = order;
        this.hasSharp = hasSharp;
    }

    public static NoteProgressionEnum getByOrder(int order) {
        return Stream.of(values()).filter(e -> {
            return e.getOrder() == order;
        }).findFirst().orElse(null);
    }

    public static NoteProgressionEnum getNextNote(NoteProgressionEnum note) {
        if (note.getOrder() == values().length) {
            return NoteProgressionEnum.getByOrder(1);
        }

        return getByOrder(note.getOrder() + 1);
    }

    public static String getSharpRepresentation(NoteProgressionEnum note) {
        return note.toString() + "#";
    }
}
