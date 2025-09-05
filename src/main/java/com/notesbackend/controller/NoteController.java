package com.notesbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; // Added for JWT
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notesbackend.dto.NoteRequestDTO;
import com.notesbackend.dto.NoteResponseDTO;
import com.notesbackend.service.NoteService;
import com.notesbackend.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final JwtUtil jwtUtil; // Inject JWT utility

    public NoteController(NoteService noteService, JwtUtil jwtUtil) {
        this.noteService = noteService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Get all notes", description = "Fetch all notes from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes fetched successfully")
    })
    @GetMapping
    public List<NoteResponseDTO> getAllNotes(@RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.getEmailFromToken(authHeader.substring(7));
        return noteService.getAllNotes(email);
    }

    @Operation(summary = "Get note by ID", description = "Fetch a specific note by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public NoteResponseDTO getNoteById(@PathVariable Long id,
                                      @RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.getEmailFromToken(authHeader.substring(7));
        return noteService.getNoteById(id, email);
    }

    @Operation(summary = "Create a new note", description = "Add a new note to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully")
    })
    @PostMapping
    public NoteResponseDTO createNote(@Valid @RequestBody NoteRequestDTO note,
                                     @RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.getEmailFromToken(authHeader.substring(7));
        return noteService.createNote(note, email);
    }

    @Operation(summary = "Update a note", description = "Update an existing note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public NoteResponseDTO updateNote(@PathVariable Long id,
                                     @Valid @RequestBody NoteRequestDTO note,
                                     @RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.getEmailFromToken(authHeader.substring(7));
        return noteService.updateNote(id, note, email);
    }

    @Operation(summary = "Delete a note", description = "Delete an existing note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id,
                           @RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.getEmailFromToken(authHeader.substring(7));
        noteService.deleteNote(id, email);
    }
}
