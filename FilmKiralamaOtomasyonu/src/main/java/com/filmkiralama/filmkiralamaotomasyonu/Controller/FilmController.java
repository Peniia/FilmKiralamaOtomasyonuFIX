package com.filmkiralama.filmkiralamaotomasyonu.Controller;



import com.filmkiralama.filmkiralamaotomasyonu.Model.Film;
import com.filmkiralama.filmkiralamaotomasyonu.Service.FilmService;

import java.util.ArrayList;
import java.util.List;

/**
 * Film listeleme, izleme ve kiralama işlemlerini yönettigimiz ontroller.
 */
public class FilmController {

    private final FilmService filmService; //film iş mantığını yöneten servis


    /**
     * FilmController constructor.
     */
    public FilmController() {
        this.filmService = new FilmService();

    }











}
