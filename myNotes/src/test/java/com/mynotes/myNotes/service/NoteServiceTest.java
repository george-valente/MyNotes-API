package com.mynotes.myNotes.service;

import com.mynotes.myNotes.DTO.NoteInputDTO;
import com.mynotes.myNotes.DTO.NoteOutputDTO;
import com.mynotes.myNotes.model.Note;
import com.mynotes.myNotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return a note based on the given id")
    void getByIdCase1() {
        Note mockNote = new Note("Test Note", "Content Note test");
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));

        NoteOutputDTO result = noteService.getById(1L);

        assertNotNull(result);
        assertEquals(mockNote.getId(), result.getId());
        assertEquals(mockNote.getTitle(), result.getTitle());
        assertEquals(mockNote.getContent(), result.getContent());
    }

    @Test
    @DisplayName("should not return a note if no id is given")
    void getByIdCase2(){
        when(noteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.getById(1L));

    }

    @Test
    void listAll() {
        Note mockNote1 = new Note("Test note", "this is a test");
        Note mockNote2 = new Note("Test not22", "this is a second test");
        List<Note> mockNotes = new ArrayList<>();

        mockNotes.add(mockNote1);
        mockNotes.add(mockNote2);

        when(noteRepository.findAll()).thenReturn(mockNotes);

        List<NoteOutputDTO> notes = noteService.listAll();

        assertNotNull(notes);
        assertEquals(2, notes.size());
        assertEquals(mockNote1.getTitle(), notes.get(0).getTitle());
        assertEquals(mockNote2.getContent(), notes.get(1).getContent());
    }

    @Test
    void create() {
        Note mockNote = new Note("Test note", "this is a test");
        when(noteRepository.save(any(Note.class))).thenReturn(mockNote);

        NoteInputDTO noteInputDTO = new NoteInputDTO("Test note", "this is a test");
        NoteOutputDTO createdNote = noteService.create(noteInputDTO);

        assertNotNull(createdNote);
        assertEquals(mockNote.getTitle(), createdNote.getTitle());
        assertEquals(mockNote.getContent(), createdNote.getContent());
    }

    @Test
    void delete() {
        Long noteId = 1L;
        Note mockNote = new Note("Test note", "this is a test");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));

        doNothing().when(noteRepository).delete(mockNote);

        noteService.delete(noteId);

        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).delete(mockNote);

    }

    @Test
    void update() {
        Long noteId = 1L;
        Note mockNote = new Note("Test note", "this is a test");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));


        NoteInputDTO noteInputDTO = new NoteInputDTO("Updated title", "Updated content");
        NoteOutputDTO updatedNote = noteService.update(noteId, noteInputDTO);

        assertNotNull(updatedNote);
        assertEquals(noteInputDTO.getTitle(), updatedNote.getTitle());
        assertEquals(noteInputDTO.getContent(), updatedNote.getContent());

        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).save(any(Note.class));

    }
}