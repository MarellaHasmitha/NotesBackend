package com.notesbackend.service;

import com.notesbackend.dto.NoteRequestDTO;
import com.notesbackend.dto.NoteResponseDTO;
import com.notesbackend.entity.Note;
import com.notesbackend.exception.ResourceNotFoundException;
import com.notesbackend.mapper.NoteMapper;
import com.notesbackend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteResponseDTO createNote(NoteRequestDTO dto) {
        Note note = NoteMapper.toEntity(dto);
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    public List<NoteResponseDTO> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public NoteResponseDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id));
        return NoteMapper.toDTO(note);
    }

    public NoteResponseDTO updateNote(Long id, NoteRequestDTO dto) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id));
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id " + id);
        }
        noteRepository.deleteById(id);
    }

    public NoteResponseDTO getNoteByShareableLink(String link) {
        Note note = noteRepository.findByShareableLink(link)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with link " + link));
        return NoteMapper.toDTO(note);
    }
}
