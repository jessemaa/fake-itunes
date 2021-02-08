package com.example.fakeitunes.fakeitunes.models;

public class PopularGenre {
    private String firstName;
    private String lastName;
    private String genreName;
    private String total;

    public PopularGenre(String firstName, String lastName, String genreName, String total) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genreName = genreName;
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    //public String getFavGenre() {
    //    return favGenre;
    //}

    //public void setFavGenre(String favGenre) {
    //    this.favGenre = favGenre;
    //}
}
