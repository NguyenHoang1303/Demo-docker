package com.example.inventoryservice.translate;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    public String translate(String key) {
        return Translator.toLocale(key);
    }
}