package org.example.service.interfaces;

public interface CDCEventConsumer {
    //Changes Data Capture, інтерфейс, який читає зміни з базою(з даними сенсорів) з черги kafka,
    // прочитавши ці зміни обробляються

    void handle(String message);
}
