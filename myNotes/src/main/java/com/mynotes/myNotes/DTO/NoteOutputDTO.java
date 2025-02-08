package com.mynotes.myNotes.DTO;

import com.mynotes.myNotes.model.Note;

public class NoteOutputDTO {
    private Long id;
    private String title;
    private String content;

    public NoteOutputDTO() {
    }

    public NoteOutputDTO(Note note){
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
