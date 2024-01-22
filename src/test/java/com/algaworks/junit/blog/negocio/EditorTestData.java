package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

// Classe Design Pat
public class EditorTestData {

    // Definido constructor para não precisar ser instânciada
    private EditorTestData() {
    }

    public static Editor umEditorNovo() {

        return new Editor(null, "Alex", "alex@email.com", BigDecimal.TEN, true);

    }

    public static Editor umEditorExistente() {

        return new Editor(1L, "Alex", "alex@email.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorComIdInexistente() {

        return new Editor(99L, "Alex", "alex@email.com", BigDecimal.TEN, true);
    }


}
