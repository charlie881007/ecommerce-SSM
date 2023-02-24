package com.yhlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhlin.bean.Listing;
import com.yhlin.enums.ListingStatus;
import com.yhlin.mapper.ListingMapper;
import com.yhlin.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {
    @Autowired
    ListingMapper listingMapper;

    @Override
    public List<Listing> getAll() {
        return listingMapper.selectAll();
    }

    @Override
    public List<Listing> getByPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return listingMapper.selectByStatus(ListingStatus.OPEN);
    }

    @Override
    public Listing getById(int listingId) {
        return listingMapper.selectByPrimaryKey(listingId);
    }

    @Override
    public int updateListing(Listing listing) {
        return listingMapper.update(listing);
    }
}
