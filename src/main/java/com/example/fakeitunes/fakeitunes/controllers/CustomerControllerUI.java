package com.example.fakeitunes.fakeitunes.controllers;

import com.example.fakeitunes.fakeitunes.dataaccess.CustomerRepository;
import com.example.fakeitunes.fakeitunes.models.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerControllerUI {

    CustomerRepository crep = new CustomerRepository();

    // Directs the user to index page, which has 5 random artists, songs and genres
    // Also has a search bar, which does not work
    @GetMapping("/")
    public String index(Model artist, Model song, Model genre, Model search){
        artist.addAttribute("artists", crep.getRandomArtists());
        song.addAttribute("songs", crep.getRandomSongs());
        genre.addAttribute("genres", crep.getRandomGenres());
        Song songs = new Song();
        search.addAttribute("song", songs);
        return "index";
    }

    // Something to try to get the search to work
    @PostMapping("/search")
    public String searchBar(@ModelAttribute Song songs, Model model) {
        Song newSong = new Song(crep.searchForSong(songs.getName()));
        model.addAttribute("song", newSong);
        return "search";
    }
}
