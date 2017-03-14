package com.bas.pgm.mongo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bas.pgm.model.HostelGuests;

public interface HostelGuestRepo extends PagingAndSortingRepository<HostelGuests, String> {

}
