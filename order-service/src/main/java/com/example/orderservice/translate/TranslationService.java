package com.example.orderservice.translate;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    public String translate(String key) {
        return Translator.toLocale(key);
    }
}