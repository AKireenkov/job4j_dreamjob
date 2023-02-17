package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {
    private IndexController indexController = new IndexController();

    @Test
    public void whenRequestGetThenReturnIndexPage() {
        assertThat(indexController.getIndex()).isEqualTo("index");
    }
}