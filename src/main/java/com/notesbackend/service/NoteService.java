package com.notesbackend.service;

import com.notesbackend.dto.NoteRequestDTO;
import com.notesbackend.dto.NoteResponseDTO;
import com.notesbackend.entity.Note;
import com.notesbackend.entity.User;
import com.notesbackend.exception.ResourceNotFoundException;
import com.notesbackend.mapper.NoteMapper;
import com.notesbackend.repository.NoteRepository;
import com.notesbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // Get all notes for a specific user
    public List<NoteResponseDTO> getAllNotes(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return noteRepository.findAllByUserId(user.getId())
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get note by ID for a specific user
    public NoteResponseDTO getNoteById(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id));

        return NoteMapper.toDTO(note);
    }

    // Create a note for a specific user
    public NoteResponseDTO createNote(NoteRequestDTO dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = NoteMapper.toEntity(dto);
        note.setUser(user); // associate note with the user
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    // Update a note for a specific user
    public NoteResponseDTO updateNote(Long id, NoteRequestDTO dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id));

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    // Delete a note for a specific user
    public void deleteNote(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id));

        noteRepository.delete(note);
    }
}
