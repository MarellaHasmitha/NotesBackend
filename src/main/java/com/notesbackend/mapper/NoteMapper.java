package com.notesbackend.mapper;

import com.notesbackend.dto.NoteRequestDTO;
import com.notesbackend.dto.NoteResponseDTO;
import com.notesbackend.entity.Note;

public class NoteMapper {

    public static Note toEntity(NoteRequestDTO dto) {
        return Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    public static NoteResponseDTO toDTO(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .shareableLink(note.getShareableLink())
                .build();
    }
}
