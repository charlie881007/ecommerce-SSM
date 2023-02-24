package com.yhlin.service;

import com.yhlin.bean.Listing;

import java.util.List;

public interface ListingService {
    List<Listing> getAll();

    List<Listing> getByPage(int page, int pageSize);

    Listing getById(int listingId);

    int updateListing(Listing listing);
}
